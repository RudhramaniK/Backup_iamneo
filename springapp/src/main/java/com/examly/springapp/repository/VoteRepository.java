package com.examly.springapp.repository;

import com.examly.springapp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPollIdAndVoterId(Long pollId, Long voterId);
    boolean existsByPollIdAndVoterId(Long pollId, Long voterId);
    Long countByPollId(Long pollId);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.poll.id = :pollId AND v.option.id = :optionId")
    Long countVotesByPollAndOption(@Param("pollId") Long pollId, @Param("optionId") Long optionId);
}