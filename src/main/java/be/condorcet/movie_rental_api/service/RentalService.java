package be.condorcet.movie_rental_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import be.condorcet.movie_rental_api.model.Rental;
import be.condorcet.movie_rental_api.repository.RentalRepository;

@Service
public class RentalService {
    
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental saveRental(Rental rental) {
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

    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    public boolean exists(Long id) {
        return rentalRepository.existsById(id);
    }
}
