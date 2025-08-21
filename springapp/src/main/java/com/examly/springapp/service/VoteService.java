package com.examly.springapp.service;

import com.examly.springapp.model.*;
import com.examly.springapp.repository.OptionRepository;
import com.examly.springapp.repository.VoteRepository;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.exception.UnauthorizedException;
import com.examly.springapp.exception.AlreadyVotedException; // Add this import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;
    
    @Autowired
    private OptionRepository optionRepository;
    
    @Autowired
    private PollService pollService;

    @Transactional
    public Vote castVote(Long pollId, Long optionId, User voter) {
        Poll poll = pollService.getPollById(pollId);
        
        // Check if user can vote in this poll
        if (!pollService.canUserVoteInPoll(pollId, voter)) {
            throw new UnauthorizedException("You cannot vote in this poll");
        }
        
        // Check if user has already voted in this poll
        if (voter != null && voteRepository.existsByPollIdAndVoterId(pollId, voter.getId())) {
            throw new AlreadyVotedException("You have already voted in this poll"); // Changed to custom exception
        }
        
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found with id: " + optionId));
        
        // Verify the option belongs to the poll
        if (!option.getPoll().getId().equals(pollId)) {
            throw new IllegalArgumentException("Option does not belong to the specified poll");
        }
        
        // Create vote record
        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setOption(option);
        vote.setVoter(voter);
        
        // Increment vote count
        option.setVoteCount(option.getVoteCount() + 1);
        optionRepository.save(option);
        
        return voteRepository.save(vote);
    }
    
    public boolean hasUserVoted(Long pollId, Long userId) {
        return voteRepository.existsByPollIdAndVoterId(pollId, userId);
    }
    
    public Long getVoteCountForPoll(Long pollId) {
        return voteRepository.countByPollId(pollId);
    }
}