package com.project.pc.service;

import com.project.pc.dto.ActivityDTO;
import com.project.pc.dto.MentorDTO;
import com.project.pc.model.Activity;
import com.project.pc.model.Mentor;
import org.springframework.stereotype.Service;

@Service
public class MappingService {
    public Activity convertDTOIntoActivity(ActivityDTO activityDTO){
        if (activityDTO == null){
            return null;
        }
        Activity activity = new Activity();
        activity.setName(activityDTO.getName());
        activity.setDescription(activityDTO.getDescription());
        return activity;
    }
    public ActivityDTO convertActivityIntoDTO(Activity activity){
        if (activity == null){
            return null;
        }
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName(activity.getName());
        activityDTO.setDescription(activity.getDescription());
        return activityDTO;
    }
    public Mentor convertDTOIntoMentor(MentorDTO mentorDTO){
        if (mentorDTO == null){
            return null;
        }
        Mentor mentor = new Mentor();
        mentor.setName(mentorDTO.getName());
        mentor.setEmail(mentorDTO.getEmail());
        return mentor;
    }
    public MentorDTO convertMentorIntoDTO(Mentor mentor){
        if (mentor == null){
            return null;
        }
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setName(mentor.getName());
        mentorDTO.setEmail(mentor.getEmail());
        return mentorDTO;
    }
}
