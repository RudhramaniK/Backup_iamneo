package com.examly.springapp.repository;
import com.examly.springapp.model.Candidate;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
     List<Candidate> findByPositionId(Long positionId);
}
