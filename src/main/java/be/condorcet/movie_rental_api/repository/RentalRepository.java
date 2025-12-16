package be.condorcet.movie_rental_api.repository;

import be.condorcet.movie_rental_api.model.Movie;
import be.condorcet.movie_rental_api.model.Rental;
import be.condorcet.movie_rental_api.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    Optional<Rental> findByUserIdAndMovieId(Long userId, Long movieId);
    List<Rental> findByUser(User user);
    boolean existsByUserAndMovie(User user, Movie movie);
}
