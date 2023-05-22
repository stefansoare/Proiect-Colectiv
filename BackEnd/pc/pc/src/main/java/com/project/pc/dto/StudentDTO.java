package com.project.pc.dto;

import lombok.Data;

@Data
public class StudentDTO {

    private long id;
    private String name;
    private String email;
    private TeamDTO teamDTO;
    private TaskDTO taskDTO;
}
