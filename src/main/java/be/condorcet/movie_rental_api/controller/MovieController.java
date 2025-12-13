package be.condorcet.movie_rental_api.controller;

import be.condorcet.movie_rental_api.model.Movie;
import be.condorcet.movie_rental_api.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id)
            .map(movie -> ResponseEntity.ok(movie))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie) {
        Movie savedMovie = movieService.saveMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @Valid @RequestBody Movie movieDetails) {
        return movieService.getMovieById(id)
            .map(existingMovie -> {
                existingMovie.setTitle(movieDetails.getTitle());
                existingMovie.setDirector(movieDetails.getDirector());
                existingMovie.setReleaseYear(movieDetails.getReleaseYear());
                existingMovie.setImdbId(movieDetails.getImdbId());
                existingMovie.setPricePerDay(movieDetails.getPricePerDay());
                existingMovie.setAvailable(movieDetails.isAvailable());

                Movie updatedMovie = movieService.saveMovie(existingMovie);
                return ResponseEntity.ok(updatedMovie);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        if (movieService.exists(id)) {
            movieService.deleteMovie(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
