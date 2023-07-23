package com.project.pc.serviceTest;

import com.project.pc.dto.GradeDTO;
import com.project.pc.model.Grade;
import com.project.pc.model.Mentor;
import com.project.pc.model.Student;
import com.project.pc.model.Task;
import com.project.pc.repository.GradeRepository;
import com.project.pc.repository.MentorRepository;
import com.project.pc.repository.StudentRepository;
import com.project.pc.repository.TaskRepository;
import com.project.pc.service.GradeService;
import com.project.pc.service.MappingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Rollback
class GradeServiceTest {
    @InjectMocks
    private GradeService gradeService;

    @Mock
    private MentorRepository mentorRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private MappingService mappingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGiveGradeWhenExistValidEntitiesAndCreatesGrade() {
        Long mentorId = 1L;
        Long studentId = 2L;
        Long taskId = 3L;
        Grade grade = new Grade();
        Mentor mentor = new Mentor(mentorId);
        Student student = new Student(studentId);
        Task task = new Task(taskId);

        when(mentorRepository.findMentorById(mentorId)).thenReturn(Optional.of(mentor));
        when(studentRepository.findStudentById(studentId)).thenReturn(Optional.of(student));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        GradeDTO result1 = gradeService.giveGrade(mentorId, studentId, taskId, grade);
        Grade result = mappingService.convetDTOIntoGrade(result1);

        assertNotNull(result);
        assertSame(mentor, result.getMentor());
        assertSame(student, result.getStudent());
        assertSame(task, result.getTask());
    }

    @Test
    void testGiveGrade_NullEntities_ReturnsNull() {
        Long mentorId = 1L;
        Long studentId = 2L;
        Long taskId = 3L;
        Grade grade = new Grade();

        when(mentorRepository.findMentorById(mentorId)).thenReturn(Optional.empty());
        when(studentRepository.findStudentById(studentId)).thenReturn(Optional.empty());
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());


        GradeDTO result1 = gradeService.giveGrade(mentorId, studentId, taskId, grade);
        Grade result = mappingService.convetDTOIntoGrade(result1);

        assertNull(result);

        verify(mentorRepository).findMentorById(mentorId);
        verify(studentRepository).findStudentById(studentId);
        verify(taskRepository).findById(taskId);
        verifyNoMoreInteractions(mentorRepository, studentRepository, taskRepository);
    }

    @Test
    void testGetStudentGradesMeanAndCalculatesMean() {
        Long taskId = 1L;
        Long studentId = 2L;
        List<Grade> grades = new ArrayList<>();
        grades.add(new Grade(85, true, "Well done!"));
        grades.add(new Grade(90, true, "Excellent!"));
        grades.add(new Grade(0, false, "Absent!"));

        when(gradeRepository.findByTaskIdAndStudentId(taskId, studentId)).thenReturn(grades);

        Long result = gradeService.getStudentGradesMean(taskId, studentId);

        assertEquals(58L, result);
    }

    @Test
    void testGetStudentGradesMean_EmptyGrades_ReturnsNull() {
        Long taskId = 1L;
        Long studentId = 2L;
        List<Grade> emptyGrades = new ArrayList<>();

        when(gradeRepository.findByTaskIdAndStudentId(taskId, studentId)).thenReturn(emptyGrades);


        Long result = gradeService.getStudentGradesMean(taskId, studentId);


        assertNull(result);

        verify(gradeRepository).findByTaskIdAndStudentId(taskId, studentId);
        verifyNoMoreInteractions(gradeRepository);
    }

    @Test
    void testGetAllStudentAttendancesAndCountsAttendances() {
        Long studentId = 2L;
        List<Grade> grades = new ArrayList<>();
        Task task1 = new Task(1L);
        Task task2 = new Task(2L);
        Task task3 = new Task(3L);
        grades.add(new Grade(true, task1));
        grades.add(new Grade(false, task2));
        grades.add(new Grade(true, task3));
        grades.add(new Grade(true, task3)); // Duplicate task ID

        when(gradeRepository.findByStudentId(studentId)).thenReturn(grades);

        Integer result = gradeService.getAllStudentAttendances(studentId);

        assertEquals(2, result);
    }

    @Test
    void testGetAllStudentAttendances_EmptyGrades_ReturnsNull() {
        Long studentId = 2L;
        List<Grade> emptyGrades = new ArrayList<>();

        when(gradeRepository.findByStudentId(studentId)).thenReturn(emptyGrades);


        Integer result = gradeService.getAllStudentAttendances(studentId);


        assertNull(result);

        verify(gradeRepository).findByStudentId(studentId);
        verifyNoMoreInteractions(gradeRepository);
    }

    @Test
    void testGetAllStudentGradesFromATaskAndConvertsToGradeDTO() {
        Long taskId = 1L;
        Long studentId = 2L;
        List<Grade> grades = new ArrayList<>();
        grades.add(new Grade());
        grades.add(new Grade());

        List<GradeDTO> expectedGradeDTOS = new ArrayList<>();
        expectedGradeDTOS.add(new GradeDTO());
        expectedGradeDTOS.add(new GradeDTO());

        when(gradeRepository.findByTaskIdAndStudentId(taskId, studentId)).thenReturn(grades);
        when(mappingService.convertGradeIntoDTO(any(Grade.class))).thenReturn(new GradeDTO());

        List<GradeDTO> result = gradeService.getAllStudentGradesFromATask(taskId, studentId);

        assertEquals(expectedGradeDTOS, result);
    }

    @Test
    void testGetAllStudentGradesFromATask_EmptyGrades_ReturnsEmptyList() {
        Long taskId = 1L;
        Long studentId = 2L;
        List<Grade> emptyGrades = new ArrayList<>();
        List<GradeDTO> expectedGradeDTOS = new ArrayList<>();

        when(gradeRepository.findByTaskIdAndStudentId(taskId, studentId)).thenReturn(emptyGrades);


        List<GradeDTO> result = gradeService.getAllStudentGradesFromATask(taskId, studentId);


        assertEquals(expectedGradeDTOS, result);

        verify(gradeRepository).findByTaskIdAndStudentId(taskId, studentId);
        verifyNoMoreInteractions(gradeRepository);
        verifyNoInteractions(mappingService);
    }

}
