package com.project.pc.dto;

import lombok.Data;

@Data
public class TaskDTO {
    private int grade;
    private String description;
    private String deadline;
    private int attendance;
    private String comment;
    private ActivityDTO activityDTO;
}
