
package com.example.resumescreening.controller;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.example.resumescreening.dto.CandidateDTO;

import com.example.resumescreening.entity.Candidate;

import com.example.resumescreening.repository.CandidateRepository;

import com.example.resumescreening.service.CandidateService;
import com.example.resumescreening.service.EmailService;
import com.example.resumescreening.util.MatchScoreUtil;
import com.example.resumescreening.util.PdfUtil;
import com.example.resumescreening.util.SkillExtractor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/save")
    public CandidateDTO saveCandidate(

            @Valid
            @RequestBody Candidate candidate) {

        return candidateService
                .saveCandidate(candidate);
    }

    @PostMapping("/uploadResume")
    public CandidateDTO uploadResume(

            @RequestParam("file")
            MultipartFile file,

            @RequestParam("name")
            String name,

            @RequestParam("email")
            String email)

            throws IOException {

        String uploadDir = "D:/uploads/";

        File directory = new File(uploadDir);

        if (!directory.exists()) {

            directory.mkdirs();
        }

        String fileName =
                file.getOriginalFilename();

        String filePath =
                uploadDir + fileName;

        File destFile =
                new File(filePath);

        file.transferTo(destFile);

        // Extract Resume Text

        String resumeText =
                PdfUtil.extractText(filePath);

        // Extract Skills

        String extractedSkills =
                SkillExtractor.extractSkills(
                        resumeText);

        // Required Skills

        String requiredSkills =
                "java,spring boot,mysql,sql";

        // Calculate Match Score

        double score =
                MatchScoreUtil.calculateScore(
                        extractedSkills,
                        requiredSkills);

        // Save Candidate

        Candidate candidate =
                new Candidate();

        candidate.setName(name);

        candidate.setEmail(email);

        candidate.setResumeFileName(
                fileName);

        candidate.setResumeText(
                resumeText);

        candidate.setSkills(
                extractedSkills);

        candidate.setMatchScore(score);

        candidate.setResumePath(
                filePath);

        CandidateDTO savedCandidate =
                candidateService
                        .saveCandidate(candidate);

        // Send Email

        emailService.sendMail(

                candidate.getEmail(),

                "Resume Uploaded Successfully",

                "Hello " + candidate.getName() +

                ", your resume has been uploaded successfully."

        );

        return savedCandidate;
        
    }

    @GetMapping("/search")
    public List<Candidate> searchCandidates(

            @RequestParam String skill) {

        return candidateService
                .searchBySkill(skill);
    }

    @GetMapping("/all")
    public List<CandidateDTO>
    getAllCandidates() {

        return candidateService
                .getAllCandidates();
    }

    @GetMapping("/topCandidates")
    public List<Candidate>
    getTopCandidates() {

        return candidateService
                .getTopCandidates();
    }

    @GetMapping("/{id}")
    public Candidate getCandidateById(

            @PathVariable Long id) {

        return candidateService
                .getCandidateById(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource>
    downloadResume(

            @PathVariable Long id)
            throws Exception {

        Candidate candidate =
                candidateRepository
                        .findById(id)
                        .orElseThrow();

        Path path =
                Paths.get(
                        candidate.getResumePath());

        Resource resource =
                new UrlResource(
                        path.toUri());

        return ResponseEntity.ok()

                .header(

                        HttpHeaders
                                .CONTENT_DISPOSITION,

                        "attachment; filename=\""
                                + resource.getFilename()
                                + "\"")

                .body(resource);
    
}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
}
