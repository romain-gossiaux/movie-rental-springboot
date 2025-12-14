package be.condorcet.movie_rental_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.condorcet.movie_rental_api.model.Rental;
import be.condorcet.movie_rental_api.service.RentalService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id)
            .map(rental -> ResponseEntity.ok(rental))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@Valid @RequestBody Rental rental) {
        Rental savedRental = rentalService.saveRental(rental);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRental);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @Valid @RequestBody Rental rentalDetails) {
        return rentalService.getRentalById(id)
            .map(existingRental -> {
                existingRental.setUser(rentalDetails.getUser());
                existingRental.setMovie(rentalDetails.getMovie());
                existingRental.setStartDate(rentalDetails.getStartDate());
                existingRental.setDurationDays(rentalDetails.getDurationDays());

                Rental updatedRental = rentalService.saveRental(existingRental);
                return ResponseEntity.ok(updatedRental);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        if (rentalService.exists(id)) {
            rentalService.deleteRental(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
