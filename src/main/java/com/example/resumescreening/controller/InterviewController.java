package com.example.resumescreening.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.resumescreening.entity.Interview;
import com.example.resumescreening.repository.InterviewRepository;
import com.example.resumescreening.service.EmailService;

@RestController
@RequestMapping("/interview")
@CrossOrigin("*")

public class InterviewController {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/schedule")
    public Interview scheduleInterview(

            @RequestBody Interview interview) {

        Interview savedInterview =
                interviewRepository.save(interview);

        // Send Email

        emailService.sendMail(

                interview.getCandidateEmail(),

                "Interview Scheduled",

                "Hello "
                        + interview.getCandidateName()

                        + ", your interview is scheduled on "

                        + interview.getDate()

                        + " at "

                        + interview.getTime()

                        + ". Meeting Link: "

                        + interview.getMeetingLink());

        return savedInterview;
    }

    @GetMapping("/all")
    public List<Interview> getAllInterviews() {

        return interviewRepository.findAll();
    }
}