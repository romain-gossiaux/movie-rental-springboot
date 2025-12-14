package be.condorcet.movie_rental_api.controller;

import be.condorcet.movie_rental_api.model.Category;
import be.condorcet.movie_rental_api.model.Movie;
import be.condorcet.movie_rental_api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
            .map(Category -> ResponseEntity.ok(Category))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/movies")
    public ResponseEntity<List<Movie>> getMoviesByCategory(@PathVariable Long id) {
        List<Movie> movies = categoryService.getMoviesByCategory(id);
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryDetails) {
        return categoryService.getCategoryById(id)
            .map(existingCategory -> {
                existingCategory.setName(categoryDetails.getName());

                Category updatedCategory = categoryService.saveCategory(existingCategory);
                return ResponseEntity.ok(updatedCategory);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryService.exists(id)) {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
