package com.example.resumescreening.util;

import java.util.ArrayList;
import java.util.List;

public class SkillExtractor {

    public static String extractSkills(String text) {

        text = text.toLowerCase();

        List<String> skills = new ArrayList<>();

        String[] skillSet = {
                "java",
                "spring boot",
                "mysql",
                "sql",
                "hibernate",
                "html",
                "css",
                "javascript",
                "react",
                "python"
        };

        for (String skill : skillSet) {

            if (text.contains(skill.toLowerCase())) {
                skills.add(skill);
            }
        }

        return String.join(", ", skills);
    }
}