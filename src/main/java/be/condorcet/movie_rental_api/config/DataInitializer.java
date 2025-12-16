package be.condorcet.movie_rental_api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import be.condorcet.movie_rental_api.model.Category;
import be.condorcet.movie_rental_api.model.Movie;
import be.condorcet.movie_rental_api.model.Role;
import be.condorcet.movie_rental_api.model.User;
import be.condorcet.movie_rental_api.repository.CategoryRepository;
import be.condorcet.movie_rental_api.repository.MovieRepository;
import be.condorcet.movie_rental_api.repository.RoleRepository;
import be.condorcet.movie_rental_api.repository.UserRepository;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(CategoryRepository categoryRepository, MovieRepository movieRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            Category action = categoryRepository.findByName("Action")
                .orElseGet(() -> categoryRepository.save(new Category("Action")));

            Category scienceFiction = categoryRepository.findByName("Science Fiction")
                .orElseGet(() -> categoryRepository.save(new Category("Science Fiction")));

            if (movieRepository.count() == 0) {
                movieRepository.save(new Movie(
                    "Blade Runner 2049",
                    "Denis Villeneuve",
                    2017,
                    scienceFiction,
                    "tt1856101",
                    new java.math.BigDecimal("3.99"),
                    "https://image.tmdb.org/t/p/original/gajva2L0rPYkEWjzgFlBXCAVBE5.jpg"
                ));

                movieRepository.save(new Movie(
                    "The Dark Knight",
                    "Christopher Nolan",
                    2008,
                    action,
                    "tt0468569",
                    new java.math.BigDecimal("2.99"),
                    "https://image.tmdb.org/t/p/original/xQPgyZOBhaz1GdCQIPf5A5VeFzO.jpg"
                ));
            }
                
            // Roles
            Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

            // Admin user
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User("admin", passwordEncoder.encode("password"));
                admin.setRoles(Set.of(adminRole));
                userRepository.save(admin);
            }

            // Simple user
            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User("user", passwordEncoder.encode("password"));
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
            }
        };
    }
}
