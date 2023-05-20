package com.project.pc.dto;

import lombok.Data;

@Data
public class TeamDTO {
    private Long teamLeader;
    private ActivityDTO activityDTO;
    private MentorDTO mentorDTO;
    private TaskDTO taskDTO;
}
