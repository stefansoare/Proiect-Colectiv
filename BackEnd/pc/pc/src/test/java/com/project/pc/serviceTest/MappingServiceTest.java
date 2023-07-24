package com.project.pc.serviceTest;

import com.project.pc.dto.*;
import com.project.pc.model.*;
import com.project.pc.service.MappingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Rollback
class MappingServiceTest {
    private MappingService mappingService;

    @Mock
    private ActivityDTO activityDTO;

    @Mock
    private Activity activity;

    @Mock
    private MentorDTO mentorDTO;

    @Mock
    private Mentor mentor;

    @Mock
    private StudentDTO studentDTO;

    @Mock
    private Student student;

    @Mock
    private Team team;

    @Mock
    private TeamDTO teamDTO;

    @Mock
    private TaskDTO taskDTO;

    @Mock
    private Task task;

    @Mock
    private GradeDTO gradeDTO;

    @Mock
    private Grade grade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mappingService = new MappingService();
    }

    @Test
    void testConvertDTOIntoActivity() {
        String name = "Test Activity";
        String description = "Test Description";

        when(activityDTO.getName()).thenReturn(name);
        when(activityDTO.getDescription()).thenReturn(description);

        Activity result = mappingService.convertDTOIntoActivity(activityDTO);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
    }

    @Test
    void testConvertActivityIntoDTO() {
        long id = 1;
        String name = "Test Activity";
        String description = "Test Description";

        when(activity.getId()).thenReturn(id);
        when(activity.getName()).thenReturn(name);
        when(activity.getDescription()).thenReturn(description);

        ActivityDTO result = mappingService.convertActivityIntoDTO(activity);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
    }

    @Test
    void testConvertDTOIntoMentor() {
        String name = "Test Mentor";
        String email = "test@example.com";

        when(mentorDTO.getName()).thenReturn(name);
        when(mentorDTO.getEmail()).thenReturn(email);

        Mentor result = mappingService.convertDTOIntoMentor(mentorDTO);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
    }

    @Test
    void testConvertMentorIntoDTO() {
        String name = "Test Mentor";
        String email = "test@example.com";

        when(mentor.getName()).thenReturn(name);
        when(mentor.getEmail()).thenReturn(email);

        MentorDTO result = mappingService.convertMentorIntoDTO(mentor);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
    }

    @Test
    void testConvertDTOIntoStudent() {
        String name = "Test Student";
        String email = "test@example.com";

        when(studentDTO.getName()).thenReturn(name);
        when(studentDTO.getEmail()).thenReturn(email);

        Student result = mappingService.convertDTOIntoStudent(studentDTO);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
    }

    @Test
    void testConvertStudentIntoDTO() {
        long id = 1;
        String name = "Test Student";
        String email = "test@example.com";

        when(student.getId()).thenReturn(id);
        when(student.getName()).thenReturn(name);
        when(student.getEmail()).thenReturn(email);
        when(student.getTeam()).thenReturn(team);
        when(team.getId()).thenReturn(2L);

        StudentDTO result = mappingService.convertStudentIntoDTO(student);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals(2L, result.getTeam_id());
    }

    @Test
    void testConvertDTOIntoTeam() {
        Long teamLeader = 1L;
        String teamName = "Test Team";

        when(teamDTO.getTeamLeader()).thenReturn(teamLeader);
        when(teamDTO.getTeamName()).thenReturn(teamName);

        Team result = mappingService.convertDTOIntoTeam(teamDTO);

        assertNotNull(result);
        assertEquals(teamLeader, result.getTeamLeader());
        assertEquals(teamName, result.getTeamName());
    }

    @Test
    void testConvertTeamIntoDTO() {
        long id = 1L;
        long teamLeader = 1L;
        String teamName = "Test Team";

        when(team.getId()).thenReturn(id);
        when(team.getTeamLeader()).thenReturn(teamLeader);
        when(team.getTeamName()).thenReturn(teamName);

        TeamDTO result = mappingService.convertTeamIntoDTO(team);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(teamLeader, result.getTeamLeader());
        assertEquals(teamName, result.getTeamName());
    }

    @Test
    void testConvertDTOIntoTask() {
        long id = 1;
        String description = "Test Task Description";
        String deadline = "2023-07-10";

        when(taskDTO.getId()).thenReturn(id);
        when(taskDTO.getDescription()).thenReturn(description);
        when(taskDTO.getDeadline()).thenReturn(deadline);

        Task result = mappingService.convertDTOIntoTask(taskDTO);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(description, result.getDescription());
        assertEquals(deadline, result.getDeadline());
    }

    @Test
    void testConvertTaskIntoDTO() {
        long id = 1;
        String description = "Test Task Description";
        String deadline = "2023-07-10";

        when(task.getId()).thenReturn(id);
        when(task.getDescription()).thenReturn(description);
        when(task.getDeadline()).thenReturn(deadline);

        TaskDTO result = mappingService.convertTaskIntoDTO(task);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(description, result.getDescription());
        assertEquals(deadline, result.getDeadline());
    }

    @Test
    void testConvertDTOIntoGrade() {
        long gradeValue = 85;
        boolean attendance = true;
        String comment = "Good job";

        when(gradeDTO.getGrade()).thenReturn(gradeValue);
        when(gradeDTO.isAttendance()).thenReturn(attendance);
        when(gradeDTO.getComment()).thenReturn(comment);

        Grade result = mappingService.convetDTOIntoGrade(gradeDTO);

        assertNotNull(result);
        assertEquals(gradeValue, result.getGrade());
        assertEquals(attendance, result.isAttendance());
        assertEquals(comment, result.getComment());
    }

    @Test
    void testConvertGradeIntoDTO() {
        long gradeValue = 85;
        boolean attendance = true;
        String date = "2023-07-10";
        String comment = "Good job";

        when(grade.getGrade()).thenReturn(gradeValue);
        when(grade.isAttendance()).thenReturn(attendance);
        when(grade.getDate()).thenReturn(date);
        when(grade.getComment()).thenReturn(comment);

        GradeDTO result = mappingService.convertGradeIntoDTO(grade);

        assertNotNull(result);
        assertEquals(gradeValue, result.getGrade());
        assertEquals(attendance, result.isAttendance());
        assertEquals(date, result.getDate());
        assertEquals(comment, result.getComment());
    }


}