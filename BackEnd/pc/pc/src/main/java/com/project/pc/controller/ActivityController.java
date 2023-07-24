package com.project.pc.controller;

import com.project.pc.dto.ActivityDTO;
import com.project.pc.exceptions.IncompleteActivityException;
import com.project.pc.exceptions.NotFoundException;
import com.project.pc.model.Activity;
import com.project.pc.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/activities/")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @PostMapping
    public ResponseEntity<?> createActivity(@RequestBody Activity activity) {
        try {
            ActivityDTO createdActivity = activityService.createActivity(activity);
            return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
        } catch (IncompleteActivityException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getAllActivities(){
        List<ActivityDTO> activities = activityService.getAllActivities();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable("id") Long id) {
        try {
            ActivityDTO activityDTO = activityService.getActivityById(id);
            return new ResponseEntity<>(activityDTO, HttpStatus.FOUND);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("name/{name}")
    public ResponseEntity<?> getActivityByName(@PathVariable("name") String name) {
        try {
            ActivityDTO activityDTO = activityService.getActivityByName(name);
            return new ResponseEntity<>(activityDTO, HttpStatus.FOUND);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateActivity(@PathVariable("id") Long id, @RequestBody ActivityDTO activityDTO) {
        try {
            Activity updated = activityService.updateActivity(id, activityDTO);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (NotFoundException | IncompleteActivityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }
    @PatchMapping("{id}")
    public ResponseEntity<?> patchActivity(@PathVariable("id") Long id, @RequestBody ActivityDTO activityDTO) {
        try {
            Activity updated = activityService.patchActivity(id, activityDTO);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }
    @DeleteMapping("name/{name}")
    public ResponseEntity<HttpStatus> deleteActivityByName(@PathVariable("name") String name){
        if (activityService.deleteActivityByName(name)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("id/{id}")
    public ResponseEntity<HttpStatus> deleteActivityById(@PathVariable("id") Long id){
        if (activityService.deleteActivityById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}