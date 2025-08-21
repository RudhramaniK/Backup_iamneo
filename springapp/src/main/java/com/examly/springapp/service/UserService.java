package com.examly.springapp.service;

import com.examly.springapp.dto.RegisterRequest;
import com.examly.springapp.model.User;
import com.examly.springapp.model.Role;
import com.examly.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email address already in use.");
        }
        
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        
        // Default role is VOTER if not specified
        if (registerRequest.getRole() == null || registerRequest.getRole().isEmpty()) {
            user.setRole(Role.VOTER);
        } else {
            try {
                user.setRole(Role.valueOf(registerRequest.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                user.setRole(Role.VOTER);
            }
        }
        
        return userRepository.save(user); // Ensure the saved user is returned
    }
}