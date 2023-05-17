package com.project.pc.dto;

import com.project.pc.model.Team;
import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private TeamDTO teamDTO;
}
