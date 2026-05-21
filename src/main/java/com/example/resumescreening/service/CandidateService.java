package com.example.resumescreening.service;

import java.util.List;

import com.example.resumescreening.entity.Candidate;

public interface CandidateService {

    Candidate saveCandidate(Candidate candidate);

    List<Candidate> searchBySkill(String skill);

    List<Candidate> getTopCandidates();
    
    List<Candidate> getAllCandidates();
}