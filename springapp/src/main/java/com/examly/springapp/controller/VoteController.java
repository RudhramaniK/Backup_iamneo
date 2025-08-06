package com.examly.springapp.controller;

import com.examly.springapp.model.Vote;
import com.examly.springapp.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<Vote> castVote(
            @RequestParam Long pollId,
            @RequestParam Long optionId,
            @RequestParam Long voterId) {
        return ResponseEntity.ok(voteService.castVote(pollId, optionId, voterId));
    }
}