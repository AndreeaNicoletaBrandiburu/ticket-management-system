package com.example.ticketmanagement.service;

import com.example.ticketmanagement.dto.UserDTO;
import com.example.ticketmanagement.model.User;
import com.example.ticketmanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creează un utilizator nou.
     */
    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }

        logger.info("Creating new user: {}", userDTO.getEmail());

        // Mapăm DTO-ul la entitate
        User user = mapToEntity(userDTO);

        // Salvăm utilizatorul în baza de date
        User savedUser = userRepository.save(user);

        logger.info("User created successfully with ID: {}", savedUser.getId());

        return mapToDTO(savedUser);
    }

    /**
     * Găsește un utilizator după ID.
     */
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToDTO);
    }

    /**
     * Returnează lista cu toți utilizatorii existenți.
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualizează datele unui utilizator existent.
     */
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());

            // Verificăm dacă s-a trimis o parolă nouă și o criptăm
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            User updatedUser = userRepository.save(user);
            logger.info("User updated successfully with ID: {}", updatedUser.getId());
            return mapToDTO(updatedUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Șterge un utilizator din baza de date.
     */
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            logger.info("User deleted successfully with ID: {}", id);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Metodă auxiliară pentru conversia din entitate `User` în `UserDTO`.
     */
    private UserDTO mapToDTO(User user) {
        if (user == null) return null;
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), null);
    }

    /**
     * Metodă auxiliară pentru conversia din `UserDTO` în entitate `User`.
     */
    private User mapToEntity(UserDTO userDTO) {
        if (userDTO == null) return null;

        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        // Setăm parola doar dacă este prezentă
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        } else {
            user.setPassword(passwordEncoder.encode("defaultPassword"));
        }

        return user;
    }
}
