package com.examly.springapp.service;

import com.examly.springapp.dto.PollRequest;
import com.examly.springapp.dto.PollResponseDTO;
import com.examly.springapp.dto.OptionDTO;
import com.examly.springapp.model.*;
import com.examly.springapp.repository.OptionRepository;
import com.examly.springapp.repository.PollRepository;
// import com.examly.springapp.repository.VoteRepository; // Temporarily disabled
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollService {
    @Autowired
    private PollRepository pollRepository;
    
    @Autowired
    private OptionRepository optionRepository;
    
    @Autowired
    private UserService userService;
    
    // @Autowired
    // private VoteRepository voteRepository; // Temporarily disabled

    @Transactional
    public PollResponseDTO createPoll(PollRequest pollRequest, User creator) {
        if (pollRequest.getOptions().size() < 2) {
            throw new IllegalArgumentException("A poll must have at least two options");
        }
        
        Poll poll = new Poll();
        poll.setQuestion(pollRequest.getQuestion());
        poll.setPrivacy(Privacy.valueOf(pollRequest.getPrivacy().toUpperCase()));
        poll.setStatus(Status.OPEN);
        poll.setCreator(creator);
        
        Poll savedPoll = pollRepository.save(poll);
        
        // Create options
        List<Option> options = pollRequest.getOptions().stream()
                .map(optionText -> {
                    Option option = new Option();
                    option.setText(optionText);
                    option.setPoll(savedPoll);
                    return option;
                })
                .collect(Collectors.toList());
        
        optionRepository.saveAll(options);
        savedPoll.setOptions(options);
        
        return convertToDTO(savedPoll, null);
    }
    
    public List<PollResponseDTO> getPublicPolls(User currentUser) {
        List<Poll> polls = pollRepository.findByPrivacy(Privacy.PUBLIC);
        return polls.stream()
                .map(poll -> convertToDTO(poll, currentUser))
                .collect(Collectors.toList());
    }
    
    public List<PollResponseDTO> getPollsByCreator(Long creatorId, User currentUser) {
        List<Poll> polls = pollRepository.findByCreatorId(creatorId);
        return polls.stream()
                .map(poll -> convertToDTO(poll, currentUser))
                .collect(Collectors.toList());
    }
    
    public Poll getPollById(Long pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(() -> new ResourceNotFoundException("Poll not found with id: " + pollId));
    }
    
    public PollResponseDTO getPollDTOById(Long pollId, User currentUser) {
        Poll poll = getPollById(pollId);
        return convertToDTO(poll, currentUser);
    }
    
    public List<PollResponseDTO> searchPolls(String query, User currentUser) {
        List<Poll> polls = pollRepository.searchPublicPolls(query);
        return polls.stream()
                .map(poll -> convertToDTO(poll, currentUser))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PollResponseDTO closePoll(Long pollId, User user) {
        Poll poll = getPollById(pollId);
        
        // Check if user is the creator or an admin
        if (!poll.getCreator().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedException("You are not authorized to close this poll");
        }
        
        poll.setStatus(Status.CLOSED);
        Poll updatedPoll = pollRepository.save(poll);
        return convertToDTO(updatedPoll, null);
    }
    
    public boolean canUserVoteInPoll(Long pollId, User user) {
        Poll poll = getPollById(pollId);
        
        // Check if poll is open
        if (!poll.getStatus().equals(Status.OPEN)) {
            return false;
        }
        
        // Check if poll is private and user is logged in
        if (poll.getPrivacy().equals(Privacy.PRIVATE) && user == null) {
            return false;
        }
        
        return true;
    }
    
    // Helper method to convert Poll entity to PollResponseDTO
    private PollResponseDTO convertToDTO(Poll poll) {
        return convertToDTO(poll, null);
    }
    
    // Helper method to convert Poll entity to PollResponseDTO with user context
    private PollResponseDTO convertToDTO(Poll poll, User currentUser) {
        List<OptionDTO> optionDTOs = poll.getOptions().stream()
                .map(option -> new OptionDTO(option.getId(), option.getText(), option.getVoteCount()))
                .collect(Collectors.toList());
        
        // TODO: Implement proper vote tracking later
        
        return new PollResponseDTO(
            poll.getId(),
            poll.getQuestion(),
            poll.getPrivacy(),
            poll.getStatus(),
            poll.getCreatedAt(),
            poll.getCreator().getId(),
            poll.getCreator().getName(),
            optionDTOs
        );
    }
}