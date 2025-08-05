package com.examly.springapp.controller;
import com.examly.springapp.model.Candidate;
import com.examly.springapp.repository.CandidateRepository;
import com.examly.springapp.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "https://8081-localhost", allowCredentials = "true")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoteService voteService;

    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @PostMapping
    public Candidate addCandidate(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }

      @GetMapping("/position/{positionId}")
    public List<Candidate> getCandidatesByPosition(@PathVariable Long positionId) {
        return candidateRepository.findByPositionId(positionId);
    }


    @PostMapping("/{id}/vote")
    public String voteCandidate(@PathVariable Long id) {
        voteService.voteForCandidate(id);
        return "Voted successfully";
    }

    @GetMapping("/{id}/votes")
    public int getVotes(@PathVariable Long id) {
        return voteService.getVotesForCandidate(id);
    }
}
