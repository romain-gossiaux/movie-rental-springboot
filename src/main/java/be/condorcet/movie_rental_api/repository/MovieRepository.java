package be.condorcet.movie_rental_api.repository;

import be.condorcet.movie_rental_api.model.Movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitle(String title);
    List<Movie> findByCategoryId(Long categoryId);
}
