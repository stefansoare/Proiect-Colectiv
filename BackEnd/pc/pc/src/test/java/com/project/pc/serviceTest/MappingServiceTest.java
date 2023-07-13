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
    void testConvertDTOIntoActivity_NonNullDTO_CreatesActivity() {
        String name = "Test Activity";
        String description = "Test Description";

        when(activityDTO.getName()).thenReturn(name);
        when(activityDTO.getDescription()).thenReturn(description);


        Activity result = mappingService.convertDTOIntoActivity(activityDTO);


        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());

        verify(activityDTO).getName();
        verify(activityDTO).getDescription();
        verifyNoMoreInteractions(activityDTO);
    }

    @Test
    void testConvertDTOIntoActivity_NullDTO_ReturnsNull() {
        activityDTO = null;

        Activity result = mappingService.convertDTOIntoActivity(activityDTO);

        assertNull(result);
    }

    @Test
    void testConvertActivityIntoDTO_NonNullActivity_CreatesDTO() {
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

        verify(activity).getId();
        verify(activity).getName();
        verify(activity).getDescription();
        verifyNoMoreInteractions(activity);
    }


    @Test
    void testConvertActivityIntoDTO_NullActivity_ReturnsNull() {
        activity = null;

        ActivityDTO result = mappingService.convertActivityIntoDTO(activity);

        assertNull(result);
    }

    @Test
    void testConvertDTOIntoMentor_NonNullDTO_CreatesMentor() {
        String name = "Test Mentor";
        String email = "test@example.com";

        when(mentorDTO.getName()).thenReturn(name);
        when(mentorDTO.getEmail()).thenReturn(email);


        Mentor result = mappingService.convertDTOIntoMentor(mentorDTO);


        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());

        verify(mentorDTO).getName();
        verify(mentorDTO).getEmail();
        verifyNoMoreInteractions(mentorDTO);
    }

    @Test
    void testConvertDTOIntoMentor_NullDTO_ReturnsNull() {
        mentorDTO = null;

        Mentor result = mappingService.convertDTOIntoMentor(mentorDTO);

        assertNull(result);
    }

    @Test
    void testConvertMentorIntoDTO_NonNullMentor_CreatesDTO() {
        String name = "Test Mentor";
        String email = "test@example.com";

        when(mentor.getName()).thenReturn(name);
        when(mentor.getEmail()).thenReturn(email);


        MentorDTO result = mappingService.convertMentorIntoDTO(mentor);


        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());

        verify(mentor).getName();
        verify(mentor).getEmail();
        verifyNoMoreInteractions(mentor);
    }

    @Test
    void testConvertMentorIntoDTO_NullMentor_ReturnsNull() {
        mentor = null;

        MentorDTO result = mappingService.convertMentorIntoDTO(mentor);

        assertNull(result);
    }

    @Test
    void testConvertDTOIntoStudent_NonNullDTO_CreatesStudent() {
        String name = "Test Student";
        String email = "test@example.com";

        when(studentDTO.getName()).thenReturn(name);
        when(studentDTO.getEmail()).thenReturn(email);


        Student result = mappingService.convertDTOIntoStudent(studentDTO);


        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());

        verify(studentDTO).getName();
        verify(studentDTO).getEmail();
        verifyNoMoreInteractions(studentDTO);
    }

    @Test
    void testConvertDTOIntoStudent_NullDTO_ReturnsNull() {
        studentDTO = null;

        Student result = mappingService.convertDTOIntoStudent(studentDTO);

        assertNull(result);
    }

    @Test
    void testConvertStudentIntoDTO_NonNullStudent_CreatesDTO() {
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

        verify(student).getId();
        verify(student).getName();
        verify(student).getEmail();
        verify(student, times(2)).getTeam();
        verify(team).getId();
        verifyNoMoreInteractions(student, team);
    }


    @Test
    void testConvertStudentIntoDTO_NullStudent_ReturnsNull() {
        student = null;

        StudentDTO result = mappingService.convertStudentIntoDTO(student);

        assertNull(result);
    }

    @Test
    void testConvertDTOIntoTeam_NonNullDTO_CreatesTeam() {
        Long teamLeader = 1L;
        String teamName = "Test Team";

        when(teamDTO.getTeamLeader()).thenReturn(teamLeader);
        when(teamDTO.getTeamName()).thenReturn(teamName);


        Team result = mappingService.convertDTOIntoTeam(teamDTO);


        assertNotNull(result);
        assertEquals(teamLeader, result.getTeamLeader());
        assertEquals(teamName, result.getTeamName());

        verify(teamDTO).getTeamLeader();
        verify(teamDTO).getTeamName();
        verifyNoMoreInteractions(teamDTO);
    }

    @Test
    void testConvertDTOIntoTeam_NullDTO_ReturnsNull() {
        teamDTO = null;

        Team result = mappingService.convertDTOIntoTeam(teamDTO);


        assertNull(result);
    }

    @Test
    void testConvertTeamIntoDTO_NonNullTeam_CreatesDTO() {
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

        verify(team).getId();
        verify(team).getTeamLeader();
        verify(team).getTeamName();
        verifyNoMoreInteractions(team);
    }

    @Test
    void testConvertTeamIntoDTO_NullTeam_ReturnsNull() {
        team = null;

        TeamDTO result = mappingService.convertTeamIntoDTO(team);

        assertNull(result);
    }

    @Test
    void testConvertDTOIntoTask_NonNullDTO_CreatesTask() {
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

        verify(taskDTO).getId();
        verify(taskDTO).getDescription();
        verify(taskDTO).getDeadline();
        verifyNoMoreInteractions(taskDTO);
    }

    @Test
    void testConvertDTOIntoTask_NullDTO_ReturnsNull() {
        taskDTO = null;

        Task result = mappingService.convertDTOIntoTask(taskDTO);

        assertNull(result);
    }

    @Test
    void testConvertTaskIntoDTO_NonNullTask_CreatesDTO() {
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

        verify(task).getId();
        verify(task).getDescription();
        verify(task).getDeadline();
        verifyNoMoreInteractions(task);
    }

    @Test
    void testConvertTaskIntoDTO_NullTask_ReturnsNull() {
        task = null;

        TaskDTO result = mappingService.convertTaskIntoDTO(task);

        assertNull(result);
    }

    @Test
    void testConvertDTOIntoGrade_NonNullDTO_CreatesGrade() {
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

        verify(gradeDTO).getGrade();
        verify(gradeDTO).isAttendance();
        verify(gradeDTO).getComment();
        verifyNoMoreInteractions(gradeDTO);
    }

    @Test
    void testConvertDTOIntoGrade_NullDTO_ReturnsNull() {
        gradeDTO = null;

        Grade result = mappingService.convetDTOIntoGrade(gradeDTO);

        assertNull(result);
    }

    @Test
    void testConvertGradeIntoDTO_NonNullGrade_CreatesDTO() {
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

        verify(grade).getGrade();
        verify(grade).isAttendance();
        verify(grade).getDate();
        verify(grade).getComment();
        verifyNoMoreInteractions(grade);
    }

    @Test
    void testConvertGradeIntoDTO_NullGrade_ReturnsNull() {
        grade = null;

        GradeDTO result = mappingService.convertGradeIntoDTO(grade);

        assertNull(result);
    }

}