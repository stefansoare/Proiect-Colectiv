package com.project.pc.service;

import com.project.pc.exception.CustomException;
import com.project.pc.model.Activity;
import com.project.pc.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
}
