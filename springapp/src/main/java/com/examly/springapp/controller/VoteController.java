package com.examly.springapp.controller;


import com.examly.springapp.dto.ApiResponse;
import com.examly.springapp.dto.VoteRequest;
import com.examly.springapp.model.User;
import com.examly.springapp.model.Vote;
import com.examly.springapp.security.UserPrincipal;
import com.examly.springapp.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/polls/{pollId}/votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<?> castVote(@PathVariable Long pollId, 
                                     @Valid @RequestBody VoteRequest voteRequest,
                                     @AuthenticationPrincipal UserPrincipal currentUser) {
        User voter = null;
        if (currentUser != null) {
            voter = new User();
            voter.setId(currentUser.getId());
        }
        
        Vote vote = voteService.castVote(pollId, voteRequest.getOptionId(), voter);
        return ResponseEntity.ok(new ApiResponse(true, "Vote cast successfully", vote));
    }
    
    @GetMapping("/has-voted")
    public ResponseEntity<?> hasUserVoted(@PathVariable Long pollId,
                                         @AuthenticationPrincipal UserPrincipal currentUser) {
        if (currentUser == null) {
            return ResponseEntity.ok(new ApiResponse(true, "User not logged in", false));
        }
        
        boolean hasVoted = voteService.hasUserVoted(pollId, currentUser.getId());
        return ResponseEntity.ok(new ApiResponse(true, "Vote status checked", hasVoted));
    }
}