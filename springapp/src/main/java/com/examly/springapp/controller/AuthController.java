package com.examly.springapp.controller;

import com.examly.springapp.dto.ApiResponse;
import com.examly.springapp.dto.LoginRequest;
import com.examly.springapp.dto.RegisterRequest;
import com.examly.springapp.model.User;
import com.examly.springapp.security.JwtTokenProvider;
import com.examly.springapp.security.UserPrincipal;
import com.examly.springapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(new ApiResponse(true, "Login successful", new UserResponse(jwt, userPrincipal.getName())));
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = userService.registerUser(registerRequest);
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully", new UserResponse(null, user.getName())));
    }
}

// New DTO for user response
class UserResponse {
    private String token;
    private String name;

    public UserResponse(String token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getToken() { return token; }
    public String getName() { return name; }
}