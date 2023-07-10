package com.project.pc.serviceTest;

import com.project.pc.dto.ActivityDTO;
import com.project.pc.dto.MentorDTO;
import com.project.pc.dto.StudentDTO;
import com.project.pc.model.Activity;
import com.project.pc.model.Mentor;
import com.project.pc.model.Student;
import com.project.pc.model.Team;
import com.project.pc.service.MappingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        verify(student, times(1)).getTeam();
        verify(team).getId();
        verifyNoMoreInteractions(student, team);
    }


    @Test
    void testConvertStudentIntoDTO_NullStudent_ReturnsNull() {
        student = null;

        StudentDTO result = mappingService.convertStudentIntoDTO(student);

        assertNull(result);
    }

}