package com.project.pc.dto;

import lombok.Data;

@Data
public class TaskDTO {
    private long id;
    private int grade;
    private String description;
    private String deadline;
    private int attendance;
    private String comment;
    private long student_id;
    private ActivityDTO activityDTO;
}
