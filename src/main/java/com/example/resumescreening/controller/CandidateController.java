package com.example.resumescreening.controller;

import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import com.example.resumescreening.util.MatchScoreUtil;
import com.example.resumescreening.util.PdfUtil;
import com.example.resumescreening.util.SkillExtractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.resumescreening.entity.Candidate;
import com.example.resumescreening.service.CandidateService;

import jakarta.validation.Valid;

import com.example.resumescreening.dto.CandidateDTO;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/save")
    public CandidateDTO saveCandidate( @Valid
            @RequestBody Candidate candidate) {

        return candidateService.saveCandidate(candidate);
    }

    @PostMapping("/uploadResume")
    public CandidateDTO uploadResume(

            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("email") String email)

            throws IOException {

        String uploadDir = "D:/uploads/";

        File directory = new File(uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = file.getOriginalFilename();

        String filePath = uploadDir + fileName;

        File destFile = new File(filePath);

        file.transferTo(destFile);

        // Extract resume text
        String resumeText =
                PdfUtil.extractText(filePath);

        // Extract skills
        String extractedSkills =
                SkillExtractor.extractSkills(resumeText);

        // Required company skills
        String requiredSkills =
                "java,spring boot,mysql,sql";

        // Calculate score
        double score =
                MatchScoreUtil.calculateScore(
                        extractedSkills,
                        requiredSkills);

        // Save candidate
        Candidate candidate = new Candidate();

        candidate.setName(name);
        candidate.setEmail(email);
        candidate.setResumeFileName(fileName);
        candidate.setResumeText(resumeText);
        candidate.setSkills(extractedSkills);
        candidate.setMatchScore(score);

        return candidateService.saveCandidate(candidate);
    }

    @GetMapping("/search")
    public List<Candidate> searchCandidates(

            @RequestParam String skill) {

        return candidateService
                .searchBySkill(skill);
    }
    @GetMapping("/all")
    public List<CandidateDTO> getAllCandidates() {

        return candidateService.getAllCandidates();
    }
    
    @GetMapping("/topCandidates")
    public List<Candidate> getTopCandidates() {

        return candidateService
                .getTopCandidates();
    }
    @GetMapping("/{id}")
    public Candidate getCandidateById(
            @PathVariable Long id) {

        return candidateService
                .getCandidateById(id);
    }
}