package com.examly.springapp.repository;

import com.examly.springapp.model.Poll;
import com.examly.springapp.model.Privacy;
import com.examly.springapp.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> findByCreatorId(Long creatorId);
    List<Poll> findByPrivacy(Privacy privacy);
    List<Poll> findByStatus(Status status);
    
    @Transactional
    @Modifying
    @Query("UPDATE Poll p SET p.status = :status WHERE p.id = :pollId")
    void updatePollStatus(Long pollId, Status status);
}