package com.project.pc.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private String name;
    private String email;
    private TeamDTO teamDTO;
    private TaskDTO taskDTO;
}
