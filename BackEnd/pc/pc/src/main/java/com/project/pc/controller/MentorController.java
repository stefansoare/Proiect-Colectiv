package com.project.pc.controller;

import com.project.pc.dto.ActivityDTO;
import com.project.pc.dto.MentorDTO;
import com.project.pc.exceptions.IncompleteActivityException;
import com.project.pc.exceptions.IncompleteMentorException;
import com.project.pc.exceptions.NotFoundException;
import com.project.pc.model.Activity;
import com.project.pc.model.Mentor;
import com.project.pc.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/mentors/")
public class MentorController {
    @Autowired
    private MentorService mentorService;
    @PostMapping
    public ResponseEntity<?> createMentor(@RequestBody Mentor mentor){
        try {
            MentorDTO createdMentor = mentorService.createMentor(mentor);
            return new ResponseEntity<>(createdMentor, HttpStatus.CREATED);
        } catch (IncompleteMentorException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<MentorDTO>> getAllMentors(){
        List<MentorDTO> mentorsDTOS = mentorService.getAllMentors();
        return new ResponseEntity<>(mentorsDTOS, HttpStatus.OK);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<?> getMentorById(@PathVariable("id") Long id) {
        try {
            MentorDTO mentorDTO = mentorService.getMentorById(id);
            return new ResponseEntity<>(mentorDTO, HttpStatus.OK);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("name/{name}")
    public ResponseEntity<?> getMentorByName(@PathVariable("name") String name){
        try {
            MentorDTO mentorDTO = mentorService.getMentorByName(name);
            return new ResponseEntity<>(mentorDTO, HttpStatus.OK);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("email/{email}")
    public ResponseEntity<?> getMentorByEmail(@PathVariable("email") String email){
        try {
            MentorDTO mentorDTO = mentorService.getMentorByEmail(email);
            return new ResponseEntity<>(mentorDTO, HttpStatus.OK);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateMentor(@PathVariable("id") Long id, @RequestBody MentorDTO mentorDTO){
        try {
            Mentor updated = mentorService.updateMentor(id, mentorDTO);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (NotFoundException | IncompleteMentorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }
    @PatchMapping("{id}")
    public ResponseEntity<?> patchMentor(@PathVariable("id") Long id, @RequestBody MentorDTO mentorDTO){
        try {
            Mentor updated = mentorService.patchMentor(id, mentorDTO);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }
    @DeleteMapping("email/{email}")
    public ResponseEntity<HttpStatus> deleteMentorByEmail(@PathVariable("email") String email){
        if (mentorService.deleteMentorByEmail(email)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("id/{id}")
    public ResponseEntity<HttpStatus> deleteMentorById(@PathVariable("id") Long id){
        if (mentorService.deleteMentorById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}