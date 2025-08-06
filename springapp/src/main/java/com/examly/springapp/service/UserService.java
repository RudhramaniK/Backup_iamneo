package com.examly.springapp.service;

import com.examly.springapp.model.User;
import com.examly.springapp.model.Role;
import com.examly.springapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Removed PasswordEncoder dependency
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // Removed password encoding
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userDetails.getName());
                    user.setEmail(userDetails.getEmail());
                    // Removed password encoding
                    if (userDetails.getPasswordHash() != null) {
                        user.setPasswordHash(userDetails.getPasswordHash());
                    }
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public long countByRole(Role role) {
        return userRepository.countByRole(role);
    }
}