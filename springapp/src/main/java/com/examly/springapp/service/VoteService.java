package com.examly.springapp.service;

import com.examly.springapp.model.*;
import com.examly.springapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.examly.springapp.model.Status;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PollRepository pollRepository;
    private final OptionRepository optionRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, 
                     PollRepository pollRepository,
                     OptionRepository optionRepository,
                     UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.pollRepository = pollRepository;
        this.optionRepository = optionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Vote castVote(Long pollId, Long optionId, Long voterId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found"));
        
        if (poll.getStatus() != Status.OPEN) {
            throw new RuntimeException("Poll is closed");
        }

        if (voteRepository.existsByPollIdAndVoterId(pollId, voterId)) {
            throw new RuntimeException("User already voted in this poll");
        }

        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("Option not found"));

        User voter = userRepository.findById(voterId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setOption(option);
        vote.setVoter(voter);
        vote.setTimestamp(java.time.LocalDateTime.now());

        optionRepository.incrementVoteCount(optionId);

        return voteRepository.save(vote);
    }
}