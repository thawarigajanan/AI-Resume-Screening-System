package com.example.resumescreening.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.resumescreening.entity.Candidate;

@Repository
public interface CandidateRepository
        extends JpaRepository<Candidate, Long> {

    List<Candidate>
    findBySkillsContaining(String skill);

    List<Candidate>
    findAllByOrderByMatchScoreDesc();
}