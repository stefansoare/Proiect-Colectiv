package com.project.pc.service;

import com.project.pc.exception.CustomException;
import com.project.pc.model.Activity;
import com.project.pc.repository.ActivityRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;
    public Activity createActivity(Activity activity) {
        return activityRepository.save(new Activity(activity.getName(), activity.getDescription()));
    }

    public List<Activity> getAllActivities() {
        List<Activity> activities = new ArrayList<>();
        activityRepository.findAll().forEach(activities::add);
        return activities;
    }

    public Activity getActivityById (Long id) {
        Optional<Activity> activityOptional = activityRepository.findById(id);
        return activityOptional.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                "There is no activity with id : " + id));
    }
    public Activity updateActivity(Long id, Activity activity){
        Activity activity1 = activityRepository.findById(id).orElse(null);
        if (activity1 == null){
            return null;
        }
        activity1.setDescription(activity.getDescription());
        activity1.setName(activity.getName());
        return activity1;
    }
    public HttpStatus deleteAllActivities(){
        activityRepository.deleteAll();
        return HttpStatus.OK;
    }
    public HttpStatus deleteActivityById(long id){
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isPresent()){
            activityRepository.deleteById(id);
            return HttpStatus.OK;
        }else {
            return HttpStatus.NOT_FOUND;
        }
    }
}
