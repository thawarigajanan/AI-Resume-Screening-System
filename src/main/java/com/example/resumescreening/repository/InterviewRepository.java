package com.example.resumescreening.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.resumescreening.entity.Interview;

public interface InterviewRepository
        extends JpaRepository<Interview, Long> {

}