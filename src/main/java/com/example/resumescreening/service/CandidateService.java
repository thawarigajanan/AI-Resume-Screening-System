package com.example.resumescreening.service;

import java.util.List;

import com.example.resumescreening.dto.CandidateDTO;
import com.example.resumescreening.entity.Candidate;

public interface CandidateService {

	CandidateDTO saveCandidate(Candidate candidate);
    
    Candidate getCandidateById(Long id);

    List<Candidate> searchBySkill(String skill);

    List<Candidate> getTopCandidates();
    
    List<CandidateDTO> getAllCandidates();
}