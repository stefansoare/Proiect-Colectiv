package com.project.pc.service;

import com.project.pc.dto.ActivityDTO;
import com.project.pc.exceptions.IncompleteActivityException;
import com.project.pc.exceptions.NotFoundException;
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
    public ActivityDTO createActivity(Activity activity) throws IncompleteActivityException, IllegalArgumentException{
        if (activity.getName() == null || activity.getDescription() == null) {
            throw new IncompleteActivityException("Activity is missing some required fields");
        }
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
    public ActivityDTO getActivityById (Long id) throws NotFoundException{  // throws
        return mappingService.convertActivityIntoDTO(activityRepository.findById(id).orElseThrow(() -> new NotFoundException("Activity not found with ID: " + id)));
    }
    public ActivityDTO getActivityByName(String name) throws NotFoundException{   // throws
        return mappingService.convertActivityIntoDTO(activityRepository.findByName(name).orElseThrow(() -> new NotFoundException("Activity not found with name: " + name)));
    }
    public Activity updateActivity(Long id, ActivityDTO activityDTO) throws NotFoundException, IncompleteActivityException  {
        Activity update = activityRepository.findById(id).orElseThrow(() -> new NotFoundException("Activity not found with ID: " + id));
        Status status = statusRepository.findById(update.getStatus().getId()).orElseThrow(() -> new NotFoundException("Status not found with ID: " + update.getStatus().getId()));
        if (activityDTO.getName() == null || activityDTO.getDescription() == null) {
            throw new IncompleteActivityException("Activity is missing some required fields");
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
    public Activity patchActivity(Long id, ActivityDTO activityDTO) throws NotFoundException {
        Activity update = activityRepository.findById(id).orElseThrow(() -> new NotFoundException("Activity not found with ID: " + id));
        Status status = statusRepository.findById(update.getStatus().getId()).orElseThrow(() -> new NotFoundException("Status not found with ID: " + update.getStatus().getId()));
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