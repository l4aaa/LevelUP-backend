package com.levelup.backend.controller;

import com.levelup.backend.entity.User;
import com.levelup.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updates) {
        return userRepo.findById(id).map(user -> {

            if (updates.getUsername() != null && !updates.getUsername().equals(user.getUsername())) {
                Optional<User> existing = userRepo.findByUsername(updates.getUsername());
                if (existing.isPresent()) {
                    return ResponseEntity.badRequest().body("Username '" + updates.getUsername() + "' is already taken.");
                }
                user.setUsername(updates.getUsername());
            }

            if (updates.getCurrentLevel() != null) {
                if (updates.getCurrentLevel() < 1) {
                    return ResponseEntity.badRequest().body("Level cannot be less than 1.");
                }
                user.setCurrentLevel(updates.getCurrentLevel());
            }

            if (updates.getCurrentXp() != null) {
                if (updates.getCurrentXp() < 0) {
                    return ResponseEntity.badRequest().body("XP cannot be negative.");
                }
                user.setCurrentXp(updates.getCurrentXp());
            }

            if (updates.getStreak() != null) {
                if (updates.getStreak() < 0) {
                    return ResponseEntity.badRequest().body("Streak cannot be negative.");
                }
                user.setStreak(updates.getStreak());
            }

            if (updates.getRole() != null) {
                String newRole = updates.getRole().toUpperCase();
                if (!newRole.equals("USER") && !newRole.equals("ADMIN")) {
                    return ResponseEntity.badRequest().body("Invalid role. Must be 'USER' or 'ADMIN'.");
                }
                user.setRole(newRole);
            }

            userRepo.save(user);
            return ResponseEntity.ok(user);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}