package com.example.resumescreening.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.resumescreening.entity.Candidate;
import com.example.resumescreening.repository.CandidateRepository;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

   
    
    @Override
    public List<Candidate> searchBySkill(
            String skill) {

        return candidateRepository
                .findBySkillsContaining(skill);
    }
    @Override
    public List<Candidate> getTopCandidates() {

        return candidateRepository
                .findAllByOrderByMatchScoreDesc();
    }



	@Override
	public List<Candidate> getAllCandidates() {
		// TODO Auto-generated method stub
		return candidateRepository.findAll();
	}
}