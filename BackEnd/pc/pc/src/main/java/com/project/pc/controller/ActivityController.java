package com.project.pc.controller;

import com.project.pc.dto.ActivityDTO;
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
    public ResponseEntity<Activity> createActivity(@RequestBody ActivityDTO activityDTO) {
        return new ResponseEntity<>(activityService.createActivity(activityDTO), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getAllActivities(){
        List<ActivityDTO> activities = activityService.getAllActivities();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable("id") Long id) {
        ActivityDTO activityDTO = activityService.getActivityById(id);
        if (activityDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(activityDTO, HttpStatus.FOUND);
    }
    @GetMapping("name/{name}")
    public ResponseEntity<ActivityDTO> getActivityByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(activityService.getActivityByName(name), HttpStatus.FOUND);
    }
    @PutMapping("{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable("id") Long id, @RequestBody ActivityDTO activityDTO){
        Activity updated = activityService.updateActivity(id, activityDTO);
        if (updated == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updated,  HttpStatus.OK);
    }
    @PatchMapping("{id}")
    public ResponseEntity<Activity> patchActivity(@PathVariable("id") Long id, @RequestBody ActivityDTO activityDTO) {
        Activity updated = activityService.patchActivity(id, activityDTO);
        if (updated == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllActivities(){
        return new ResponseEntity<>(activityService.deleteAllActivities());
    }
    @DeleteMapping("name/{name}")
    public ResponseEntity<HttpStatus> deleteActivityByName(@PathVariable("name") String name){
        return new ResponseEntity<>(activityService.deleteActivityByName(name));
    }
    @DeleteMapping("id/{id}")
    public ResponseEntity<HttpStatus> deleteActivityById(@PathVariable("id") Long id){
        return new ResponseEntity<>(activityService.deleteActivityById(id));
    }
}
