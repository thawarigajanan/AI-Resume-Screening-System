package com.example.resumescreening.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.resumescreening.service.AIService;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @GetMapping("/questions")
    public String generateQuestions(
            @RequestParam String skills) {

        return aiService.generateQuestions(skills);
    }

    @GetMapping("/resume-analysis")
    public String analyzeResume(
            @RequestParam String text) {

        return aiService.analyzeResume(text);
    }

    @GetMapping("/summary")
    public String summary(
            @RequestParam String text) {

        return aiService.candidateSummary(text);
    }
}