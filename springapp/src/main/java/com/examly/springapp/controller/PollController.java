package com.examly.springapp.controller;

import com.examly.springapp.dto.ApiResponse;
import com.examly.springapp.dto.PollRequest;
import com.examly.springapp.dto.PollResponseDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.model.Role;
import com.examly.springapp.security.UserPrincipal;
import com.examly.springapp.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/polls")
public class PollController {
    @Autowired
    private PollService pollService;

    @GetMapping
    public List<PollResponseDTO> getPublicPolls(@AuthenticationPrincipal UserPrincipal currentUser) {
        // For now, pass null to avoid complex user object creation
        return pollService.getPublicPolls(null);
    }
    
    @GetMapping("/search")
    public List<PollResponseDTO> searchPolls(@RequestParam String query, @AuthenticationPrincipal UserPrincipal currentUser) {
        // For now, pass null to avoid complex user object creation
        return pollService.searchPolls(query, null);
    }
    
    @GetMapping("/my-polls")
    public List<PollResponseDTO> getMyPolls(@AuthenticationPrincipal UserPrincipal currentUser) {
        // For now, pass null to avoid complex user object creation
        return pollService.getPollsByCreator(currentUser.getId(), null);
    }
    
    @GetMapping("/{pollId}")
    public PollResponseDTO getPoll(@PathVariable Long pollId, @AuthenticationPrincipal UserPrincipal currentUser) {
        // For now, pass null to avoid complex user object creation
        return pollService.getPollDTOById(pollId, null);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('CREATOR')")
    public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequest pollRequest, 
                                       @AuthenticationPrincipal UserPrincipal currentUser) {
        User creator = new User();
        creator.setId(currentUser.getId());
        
        PollResponseDTO pollDTO = pollService.createPoll(pollRequest, creator);
        return ResponseEntity.ok(new ApiResponse(true, "Poll created successfully", pollDTO));
    }
    
    @PostMapping("/{pollId}/close")
    public ResponseEntity<?> closePoll(@PathVariable Long pollId, 
                                      @AuthenticationPrincipal UserPrincipal currentUser) {
        User user = new User();
        user.setId(currentUser.getId());
        user.setRole(Role.valueOf(currentUser.getRole()));
        
        PollResponseDTO pollDTO = pollService.closePoll(pollId, user);
        return ResponseEntity.ok(new ApiResponse(true, "Poll closed successfully", pollDTO));
    }
}