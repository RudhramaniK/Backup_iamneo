package com.examly.springapp.service;

import com.examly.springapp.model.Poll;
import com.examly.springapp.model.Status;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.PollRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private final PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll createPoll(Poll poll) {
        poll.setStatus(Status.OPEN);
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
    }

    public List<Poll> getPollsByCreator(User creator) {
        return pollRepository.findByCreatorId(creator.getId());
    }

    public Poll updatePollStatus(Long id, Status status) {
        Poll poll = pollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poll not found"));
        poll.setStatus(status);
        return pollRepository.save(poll);
    }

    public void deletePoll(Long id) {
        pollRepository.deleteById(id);
    }
}