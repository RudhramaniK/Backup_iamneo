package com.examly.springapp.repository;

import com.examly.springapp.model.Vote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByPollIdAndVoterId(Long pollId, Long voterId);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.poll.id = :pollId")
    int countVotesByPollId(Long pollId);
    
    @Query("SELECT v.option.id, COUNT(v) FROM Vote v WHERE v.poll.id = :pollId GROUP BY v.option.id")
    List<Object[]> getVoteCountsByPollId(Long pollId);
}