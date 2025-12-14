package be.condorcet.movie_rental_api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import be.condorcet.movie_rental_api.model.Role;
import be.condorcet.movie_rental_api.model.User;
import be.condorcet.movie_rental_api.repository.RoleRepository;
import be.condorcet.movie_rental_api.repository.UserRepository;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {

            // Roles
            Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

            // Admin user
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User("admin", "password");
                admin.setRoles(Set.of(adminRole));
                userRepository.save(admin);
            }

            // Simple user
            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User("user", "password");
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
            }
        };
    }
}
