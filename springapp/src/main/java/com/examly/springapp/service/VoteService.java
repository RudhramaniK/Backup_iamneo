package com.examly.springapp.service;

import com.examly.springapp.model.Candidate;
import com.examly.springapp.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private CandidateRepository candidateRepository;

    public void voteForCandidate(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        candidate.setVotes(candidate.getVotes() + 1);
        candidateRepository.save(candidate);
    }

    public int getVotesForCandidate(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .map(Candidate::getVotes)
                .orElse(0);
    }
}