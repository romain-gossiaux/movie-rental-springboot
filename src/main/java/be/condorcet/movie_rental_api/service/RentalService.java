package be.condorcet.movie_rental_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import be.condorcet.movie_rental_api.model.Rental;
import be.condorcet.movie_rental_api.model.User;
import be.condorcet.movie_rental_api.model.Movie;
import be.condorcet.movie_rental_api.repository.MovieRepository;
import be.condorcet.movie_rental_api.repository.RentalRepository;
import be.condorcet.movie_rental_api.repository.UserRepository;

@Service
public class RentalService {
    
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public List<Rental> getRentalsByUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return rentalRepository.findByUser(user);
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental createRental(Rental rental, Authentication authentication) {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        rental.setUser(user);

        Movie movie = movieRepository.findById(rental.getMovie().getId())
            .orElseThrow(() -> new RuntimeException("Film non trouvé"));
        rental.setMovie(movie);

        boolean alreadyExists = rentalRepository.existsByUserAndMovie(rental.getUser(), rental.getMovie());
        if (alreadyExists) {
            throw new IllegalStateException("L'utilisateur a déjà loué ce film");
        }

        rental.setTotalPrice(
            rental.getMovie().getPricePerDay()
                .multiply(BigDecimal.valueOf(rental.getDurationDays()))
        );
        return rentalRepository.save(rental);
    }

    public Rental updateRental(Long id, Rental rentalDetails) {

        Rental rental = rentalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rental non trouvée"));

        rental.setMovie(
            movieRepository.findById(rentalDetails.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Film non trouvé"))
        );

        rental.setStartDate(rentalDetails.getStartDate());
        rental.setDurationDays(rentalDetails.getDurationDays());

        rental.setTotalPrice(
            rental.getMovie().getPricePerDay()
                .multiply(BigDecimal.valueOf(rental.getDurationDays()))
        );

        return rentalRepository.save(rental);
    }


    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    public boolean exists(Long id) {
        return rentalRepository.existsById(id);
    }
}
