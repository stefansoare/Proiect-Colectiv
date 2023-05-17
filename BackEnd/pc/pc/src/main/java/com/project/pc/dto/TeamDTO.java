package com.project.pc.dto;

import com.project.pc.model.Activity;
import lombok.Data;

@Data
public class TeamDTO {
    private ActivityDTO activityDTO;
    private MentorDTO mentorDTO;
}
