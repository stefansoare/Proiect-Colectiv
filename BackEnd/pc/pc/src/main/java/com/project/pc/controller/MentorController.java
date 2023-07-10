package com.project.pc.controller;

import com.project.pc.dto.MentorDTO;
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
    public ResponseEntity<MentorDTO> createMentor(@RequestBody Mentor mentor){
        return new ResponseEntity<>(mentorService.createMentor(mentor), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<MentorDTO>> getAllMentors(){
        List<MentorDTO> mentorsDTOS = mentorService.getAllMentors();
        return new ResponseEntity<>(mentorsDTOS, HttpStatus.OK);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<MentorDTO> getMentorById(@PathVariable("id") Long id){
        MentorDTO mentorDTO = mentorService.getMentorById(id);
        if (mentorDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mentorDTO, HttpStatus.OK);
    }
    @GetMapping("name/{name}")
    public ResponseEntity<MentorDTO> getMentorByName(@PathVariable("name") String name){
        MentorDTO mentorDTO = mentorService.getMentorByName(name);
        if (mentorDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mentorDTO, HttpStatus.OK);
    }
    @GetMapping("email/{email}")
    public ResponseEntity<MentorDTO> getMentorByEmail(@PathVariable("email") String email){
        MentorDTO mentorDTO = mentorService.getMentorByEmail(email);
        if (mentorDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mentorDTO, HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<Mentor> updateMentor(@PathVariable("id") Long id, @RequestBody MentorDTO mentorDTO){
        Mentor update = mentorService.updateMentor(id, mentorDTO);
        if (update == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
    @PatchMapping("{id}")
    public ResponseEntity<Mentor> patchMentor(@PathVariable("id") Long id, @RequestBody MentorDTO mentorDTO){
        Mentor update = mentorService.patchMentor(id, mentorDTO);
        if (update == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(update, HttpStatus.OK);
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