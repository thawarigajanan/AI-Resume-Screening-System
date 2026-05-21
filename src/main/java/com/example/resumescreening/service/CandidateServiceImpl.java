package com.example.resumescreening.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.resumescreening.entity.Candidate;
import com.example.resumescreening.exception.ResourceNotFoundException;
import com.example.resumescreening.repository.CandidateRepository;

import org.modelmapper.ModelMapper;

import com.example.resumescreening.dto.CandidateDTO;
@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public CandidateDTO saveCandidate(
            Candidate candidate) {

        Candidate savedCandidate =
                candidateRepository.save(candidate);

        return modelMapper.map(
                savedCandidate,
                CandidateDTO.class);
    }

    @Override
    public Candidate getCandidateById(Long id) {

        return candidateRepository.findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Candidate not found with id: " + id));
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
    public List<CandidateDTO> getAllCandidates() {

        return candidateRepository.findAll()

                .stream()

                .map(candidate ->

                        modelMapper.map(
                                candidate,
                                CandidateDTO.class))

                .toList();
    }
}