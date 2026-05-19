package com.example.resumescreening.util;

public class MatchScoreUtil {

    public static double calculateScore(
            String candidateSkills,
            String requiredSkills) {

        String[] candidate =
                candidateSkills.toLowerCase().split(",");

        String[] required =
                requiredSkills.toLowerCase().split(",");

        int matched = 0;

        for (String req : required) {

            for (String cand : candidate) {

                if (req.trim().equals(cand.trim())) {
                    matched++;
                }
            }
        }

        return ((double) matched / required.length) * 100;
    }
}