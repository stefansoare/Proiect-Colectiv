package com.project.pc.controller;

import com.project.pc.model.Activity;
import com.project.pc.repository.ActivityRepository;
import com.project.pc.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityRepository activityRepository;
    private Long id;

    @PostMapping("/createActivity")
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        Activity createdActivity = activityService.createActivity(activity);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    @GetMapping("/activities")
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        if(activities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping("/activity/{actId}")
    public Activity getActivity(@PathVariable("actId") Long id) {
        return activityService.getActivityById(id);
    }

        @PutMapping("/activity/{activityID}")
    public ResponseEntity<Activity> updateActivity(@PathVariable("activityID") Long activityID, @RequestBody Activity activity){
        Activity activity1 = activityService.updateActivity(activityID, activity);
        if (activity1 == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(activityRepository.save(activity1), HttpStatus.OK);
    }
    @DeleteMapping("/activity")
    public ResponseEntity<HttpStatus> deleteteAllActivities(){
        return new ResponseEntity<>(activityService.deleteAllActivities());
    }
    @DeleteMapping("/activity/{activityId}")
    public ResponseEntity<HttpStatus> deleteActivityById(@PathVariable("activityId") Long id){
        return new ResponseEntity<>(activityService.deleteActivityById(id));
    }
}
