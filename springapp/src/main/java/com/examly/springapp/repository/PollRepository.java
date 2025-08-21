package com.examly.springapp.repository;



import com.examly.springapp.model.Poll;
import com.examly.springapp.model.Privacy;
import com.examly.springapp.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> findByPrivacy(Privacy privacy);
    List<Poll> findByStatus(Status status);
    List<Poll> findByCreatorId(Long creatorId);
    List<Poll> findByPrivacyAndStatus(Privacy privacy, Status status);
    
    @Query("SELECT p FROM Poll p WHERE p.question LIKE %:query% AND p.privacy = 'PUBLIC'")
    List<Poll> searchPublicPolls(@Param("query") String query);
}