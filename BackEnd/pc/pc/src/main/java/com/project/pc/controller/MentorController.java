package com.project.pc.controller;

import com.project.pc.model.Mentor;
import com.project.pc.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class MentorController {
    @Autowired
    private MentorService mentorService;

    private Long id;

    @PostMapping("/createMentor")
    public ResponseEntity<Mentor> createMentor(@RequestBody Mentor mentor) {
        Mentor createMentor = mentorService.createMentor(mentor);
        return new ResponseEntity<>(createMentor, HttpStatus.CREATED);
    }

    @GetMapping("/mentors")
    public ResponseEntity<List<Mentor>> getAllMentors() {
        List<Mentor> mentors = mentorService.getAllMentors();
        if (mentors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }

    @GetMapping("/mentor/{menId}")
    public Mentor getMentor(@PathVariable("menId") Long id) {
        return mentorService.getMentorById(id);
    }
}
