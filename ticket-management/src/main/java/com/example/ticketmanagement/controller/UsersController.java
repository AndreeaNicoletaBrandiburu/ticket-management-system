package com.example.ticketmanagement.controller;

import com.example.ticketmanagement.dto.UserDTO;
import com.example.ticketmanagement.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "Users API", description = "Endpoints pentru gestionarea utilizatorilor") // Swagger Documentation
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    /**
     * Creare utilizator nou
     */
    @Operation(summary = "Creează un utilizator nou")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("Received request to create user: {}", userDTO);

        UserDTO newUser = usersService.createUser(userDTO);
        if (newUser == null) {
            logger.warn("User creation failed for: {}", userDTO);
            return ResponseEntity.badRequest().build();
        }

        logger.info("User created successfully: {}", newUser);
        return ResponseEntity.ok(newUser);
    }

    /**
     * Obține un utilizator după ID
     */
    @Operation(summary = "Obține un utilizator după ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);

        Optional<UserDTO> user = usersService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obține toți utilizatorii
     */
    @Operation(summary = "Obține toți utilizatorii")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        logger.info("Fetching all users...");
        return usersService.getAllUsers();
    }

    /**
     * Actualizează un utilizator
     */
    @Operation(summary = "Actualizează un utilizator existent")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        try {
            logger.info("Updating user with ID: {}", id);

            UserDTO updatedUser = usersService.updateUser(id, userDTO);
            if (updatedUser == null) {
                logger.warn("User update failed, user not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }

            logger.info("User updated successfully: {}", updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            logger.error("Error updating user with ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Șterge un utilizator după ID
     */
    @Operation(summary = "Șterge un utilizator după ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);

        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
