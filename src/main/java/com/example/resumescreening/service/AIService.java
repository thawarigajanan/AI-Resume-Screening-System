package com.example.resumescreening.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final ChatClient chatClient;

    public AIService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    // INTERVIEW QUESTIONS
    public String generateQuestions(String skills) {

        return chatClient.prompt()
                .user("Generate interview questions for " + skills)
                .call()
                .content();
    }

    // RESUME ANALYSIS
    public String analyzeResume(String text) {

        return chatClient.prompt()
                .user("Analyze this resume: " + text)
                .call()
                .content();
    }

    // CANDIDATE SUMMARY
    public String candidateSummary(String text) {

        return chatClient.prompt()
                .user("Create candidate summary for: " + text)
                .call()
                .content();
    }
}