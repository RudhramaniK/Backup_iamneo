package com.examly.springapp.controller;

import com.examly.springapp.model.Poll;
import com.examly.springapp.model.Status;
import com.examly.springapp.service.PollService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        return ResponseEntity.ok(pollService.createPoll(poll));
    }

    @GetMapping
    public ResponseEntity<List<Poll>> getAllPolls() {
        return ResponseEntity.ok(pollService.getAllPolls());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPollById(@PathVariable Long id) {
        return pollService.getPollById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Poll> updatePollStatus(
            @PathVariable Long id, 
            @RequestParam Status status) {
        return ResponseEntity.ok(pollService.updatePollStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable Long id) {
        pollService.deletePoll(id);
        return ResponseEntity.noContent().build();
    }
}