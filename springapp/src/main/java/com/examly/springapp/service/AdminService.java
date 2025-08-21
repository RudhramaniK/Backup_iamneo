package com.examly.springapp.service;


import com.examly.springapp.model.Poll;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.PollRepository;
import com.examly.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PollRepository pollRepository;
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }
    
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    
    public void deletePoll(Long pollId) {
        pollRepository.deleteById(pollId);
    }
}