package ru.dmitriev.lab.java_telg_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmitriev.lab.java_telg_bot.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
