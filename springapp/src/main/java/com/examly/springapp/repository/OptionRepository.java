package com.examly.springapp.repository;

import com.examly.springapp.model.Option;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByPollId(Long pollId);
    
    @Transactional
    @Modifying
    @Query("UPDATE Option o SET o.voteCount = o.voteCount + 1 WHERE o.id = :optionId")
    void incrementVoteCount(Long optionId);
}