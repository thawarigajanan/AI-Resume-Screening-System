package com.example.resumescreening.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SkillRecommendationService {

    public List<String> recommendSkills(
            String skills) {

        List<String> recommendations =
                new ArrayList<>();

        skills = skills.toLowerCase();

        if (skills.contains("java")) {

            recommendations.add("Spring Boot");
            recommendations.add("Hibernate");
            recommendations.add("Microservices");
        }

        if (skills.contains("react")) {

            recommendations.add("Redux");
            recommendations.add("Next.js");
            recommendations.add("TypeScript");
        }

        if (skills.contains("mysql")) {

            recommendations.add("MongoDB");
            recommendations.add("PostgreSQL");
        }

        return recommendations;
    }
}