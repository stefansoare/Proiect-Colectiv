package com.project.pc.service;

import com.project.pc.model.Activity;
import com.project.pc.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    public Activity createActivity(Activity activity){
        return activityRepository.save(new Activity(activity.getName(), activity.getDescription()));
    }
    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }
    public Activity getActivityById (Long id) {
        return activityRepository.findById(id).orElse(null);
    }
    public Activity updateActivity (Long id, Activity activity){
        Activity update = activityRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        update.setName(activity.getName());
        update.setDescription(activity.getDescription());
        activityRepository.save(update);
        return update;
    }
    public Activity patchActivity(long id, Activity activity) {
        Activity update = activityRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        if (activity.getName() != null) {
            update.setName(activity.getName());
        }
        if (activity.getDescription() != null) {
            update.setDescription(activity.getDescription());
        }
        activityRepository.save(update);
        return update;
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
            return HttpStatus.BAD_REQUEST;
        }
    }
}
