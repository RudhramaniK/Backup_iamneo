package com.examly.springapp.controller;


import com.examly.springapp.dto.ApiResponse;
import com.examly.springapp.model.Poll;
import com.examly.springapp.exception.UnauthorizedException;
import com.examly.springapp.model.User;
import com.examly.springapp.security.UserPrincipal;
import com.examly.springapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public List<User> getAllUsers(@AuthenticationPrincipal UserPrincipal currentUser) {
        // Check if user is admin
        if (!currentUser.getRole().equals("ADMIN")) {
            throw new UnauthorizedException("Access denied. Admin role required.");
        }
        
        return adminService.getAllUsers();
    }
    
    @GetMapping("/polls")
    public List<Poll> getAllPolls(@AuthenticationPrincipal UserPrincipal currentUser) {
        // Check if user is admin
        if (!currentUser.getRole().equals("ADMIN")) {
            throw new UnauthorizedException("Access denied. Admin role required.");
        }
        
        return adminService.getAllPolls();
    }
    
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, 
                                       @AuthenticationPrincipal UserPrincipal currentUser) {
        // Check if user is admin
        if (!currentUser.getRole().equals("ADMIN")) {
            throw new UnauthorizedException("Access denied. Admin role required.");
        }
        
        adminService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully"));
    }
    
    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId, 
                                       @AuthenticationPrincipal UserPrincipal currentUser) {
        // Check if user is admin
        if (!currentUser.getRole().equals("ADMIN")) {
            throw new UnauthorizedException("Access denied. Admin role required.");
        }
        
        adminService.deletePoll(pollId);
        return ResponseEntity.ok(new ApiResponse(true, "Poll deleted successfully"));
    }
}