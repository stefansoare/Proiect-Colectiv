package com.project.pc.serviceTest;

import com.project.pc.dto.GradeDTO;
import com.project.pc.exceptions.NoGradesFoundException;
import com.project.pc.exceptions.NotFoundException;
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
    public void testGiveGradeValidIds() {
        Long mId = 1L;
        Long sId = 2L;
        Long tId = 3L;
        Grade grade = new Grade();

        Mentor mentor = new Mentor();
        mentor.setId(mId);
        Student student = new Student();
        student.setId(sId);
        Task task = new Task();
        task.setId(tId);

        grade.setMentor(mentor);
        grade.setStudent(student);
        grade.setTask(task);

        when(mentorRepository.findMentorById(mId)).thenReturn(Optional.of(mentor));
        when(studentRepository.findStudentById(sId)).thenReturn(Optional.of(student));
        when(taskRepository.findById(tId)).thenReturn(Optional.of(task));
        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);
        when(mappingService.convertGradeIntoDTO(any(Grade.class))).thenReturn(new GradeDTO());

        GradeDTO result = gradeService.giveGrade(mId, sId, tId, grade);

        assertEquals(mentor, grade.getMentor());
        assertEquals(student, grade.getStudent());
        assertEquals(task, grade.getTask());
        assertEquals(result, mappingService.convertGradeIntoDTO(grade));
    }

    @Test
    public void testGiveGradeInvalidIds() {
        Long mId = 10L; // Invalid Mentor ID
        Long sId = 20L; // Invalid Student ID
        Long tId = 30L; // Invalid Task ID
        Grade grade = new Grade();

        when(mentorRepository.findMentorById(mId)).thenReturn(Optional.empty());
        when(studentRepository.findStudentById(sId)).thenReturn(Optional.empty());
        when(taskRepository.findById(tId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> gradeService.giveGrade(mId, sId, tId, grade));
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
    void testGetStudentGradesMeanNoGradesFound() {
        Long tId = 1L;
        Long sId = 2L;
        when(gradeRepository.findByTaskIdAndStudentId(tId, sId)).thenReturn(new ArrayList<>());
        assertThrows(NoGradesFoundException.class, () -> gradeService.getStudentGradesMean(tId, sId));
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
    void testGetAllStudentAttendancesNoGradesFound() {
        Long studentId = 2L;
        when(gradeRepository.findByStudentId(studentId)).thenReturn(new ArrayList<>());
        assertThrows(NoGradesFoundException.class, () -> gradeService.getAllStudentAttendances(studentId));
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
    void testGetAllStudentGradesFromATaskEmptyGradesAndReturnsEmptyList() {
        Long taskId = 1L;
        Long studentId = 2L;
        List<Grade> emptyGrades = new ArrayList<>();
        List<GradeDTO> expectedGradeDTOS = new ArrayList<>();

        when(gradeRepository.findByTaskIdAndStudentId(taskId, studentId)).thenReturn(emptyGrades);
        List<GradeDTO> result = gradeService.getAllStudentGradesFromATask(taskId, studentId);
        assertEquals(expectedGradeDTOS, result);
    }

    @Test
    public void testGetAttendanceForTaskWithAttendance() {
        Long tId = 1L;
        Long sId = 2L;

        Grade grade1 = new Grade();
        grade1.setAttendance(false);
        Grade grade2 = new Grade();
        grade2.setAttendance(true);

        List<Grade> grades = new ArrayList<>();
        grades.add(grade1);
        grades.add(grade2);

        when(gradeRepository.findByTaskIdAndStudentId(tId, sId)).thenReturn(grades);
        boolean result = gradeService.getAttendanceForTask(tId, sId);
        assertTrue(result);
    }

    @Test
    public void testGetAttendanceForTaskWithoutAttendance() {
        Long tId = 1L;
        Long sId = 2L;

        Grade grade1 = new Grade();
        grade1.setAttendance(false);
        Grade grade2 = new Grade();
        grade2.setAttendance(false);

        List<Grade> grades = new ArrayList<>();
        grades.add(grade1);
        grades.add(grade2);

        when(gradeRepository.findByTaskIdAndStudentId(tId, sId)).thenReturn(grades);
        boolean result = gradeService.getAttendanceForTask(tId, sId);
        assertFalse(result);
    }

    @Test
    public void testGetAttendanceForTaskNoGradesFound() {
        Long tId = 1L;
        Long sId = 2L;

        when(gradeRepository.findByTaskIdAndStudentId(tId, sId)).thenReturn(new ArrayList<>());
        boolean result = gradeService.getAttendanceForTask(tId, sId);
        assertFalse(result);
    }
}
