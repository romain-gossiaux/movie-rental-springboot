package be.condorcet.movie_rental_api.service;

import be.condorcet.movie_rental_api.model.Category;
import be.condorcet.movie_rental_api.model.Movie;
import be.condorcet.movie_rental_api.repository.CategoryRepository;
import be.condorcet.movie_rental_api.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;

    public CategoryService(CategoryRepository categoryRepository, MovieRepository movieRepository) {
        this.categoryRepository = categoryRepository;
        this.movieRepository = movieRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Movie> getMoviesByCategory(Long categoryId) {
        return movieRepository.findByCategoryId(categoryId);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean exists(Long id) {
        return categoryRepository.existsById(id);
    }
}
