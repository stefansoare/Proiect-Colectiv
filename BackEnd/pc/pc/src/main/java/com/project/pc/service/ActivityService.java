package com.project.pc.service;

import com.project.pc.dto.ActivityDTO;
import com.project.pc.model.Activity;
import com.project.pc.model.Status;
import com.project.pc.repository.ActivityRepository;
import com.project.pc.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private MappingService mappingService;
    public ActivityDTO createActivity(Activity activity){
        if (activity == null)
            return null;
        Status status = new Status();
        statusRepository.save(status);
        activity.setStatus(status);
        activityRepository.save(activity);
        return mappingService.convertActivityIntoDTO(activity);
    }
    public List<ActivityDTO> getAllActivities(){
        List<Activity> activities = activityRepository.findAll();
        List<ActivityDTO> activityDTOS = new ArrayList<>();
        for(Activity activity : activities){
            activityDTOS.add(mappingService.convertActivityIntoDTO(activity));
        }
        return activityDTOS;
    }
    public ActivityDTO getActivityById (Long id) {
        return mappingService.convertActivityIntoDTO(activityRepository.findById(id).orElse(null));
    }
    public ActivityDTO getActivityByName(String name){
        return mappingService.convertActivityIntoDTO(activityRepository.findByName(name).orElse(null));
    }
    public Activity updateActivity (Long id, ActivityDTO activityDTO){
        Activity update = activityRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        Status status = statusRepository.findById(update.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        update.setName(activityDTO.getName());
        update.setDescription(activityDTO.getDescription());
        update.setStatus(status);
        activityRepository.save(update);
        return update;
    }
    public Activity patchActivity(Long id, ActivityDTO activityDTO) {
        Activity update = activityRepository.findById(id).orElse(null);
        if (update == null) {
            return null;
        }
        Status status = statusRepository.findById(update.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        if (activityDTO.getName() != null) {
            update.setName(activityDTO.getName());
        }
        if (activityDTO.getDescription() != null) {
            update.setDescription(activityDTO.getDescription());
        }
        update.setStatus(status);
        activityRepository.save(update);
        return update;
    }
    public boolean deleteActivityByName(String name){
        Optional<Activity> activity = activityRepository.findByName(name);
        if (activity.isPresent()){
            activityRepository.deleteById(activity.get().getId());
            return true;
        }
        return false;
    }
    public boolean deleteActivityById(Long id){
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isPresent()){
            activityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}