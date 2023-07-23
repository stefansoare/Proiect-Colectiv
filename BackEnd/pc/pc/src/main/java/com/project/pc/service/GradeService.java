package com.project.pc.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private MappingService mappingService;
    public GradeDTO giveGrade(Long mId, Long sId, Long tId, Grade grade) throws NotFoundException {
        Mentor mentor = mentorRepository.findMentorById(mId).orElse(null);
        Student student = studentRepository.findStudentById(sId).orElse(null);
        Task task = taskRepository.findById(tId).orElse(null);
        if (mentor == null || student == null || task == null){
            throw new NotFoundException("Invalid IDs. Check again!");
        }
        grade.setDate();
        grade.setMentor(mentor);
        grade.setStudent(student);
        grade.setTask(task);
        mentor.addToGrades(grade);
        student.addToGrades(grade);
        task.addToGrades(grade);
        gradeRepository.save(grade);
        mentorRepository.save(mentor);
        studentRepository.save(student);
        taskRepository.save(task);
        return mappingService.convertGradeIntoDTO(grade);
    }
    public Long getStudentGradesMean(Long tId, Long sId) throws NoGradesFoundException {
        List<Grade> grades = gradeRepository.findByTaskIdAndStudentId(tId, sId);
        if (grades.isEmpty()){
            throw new NoGradesFoundException("No grades found.");
        }
        Long sum = 0L;
        int length = grades.size();
        for (Grade grade : grades){
            sum = sum + grade.getGrade();
        }
        return sum/length;
    }
    public Integer getAllStudentAttendances(Long sId) throws NoGradesFoundException{
        List<Grade> grades = gradeRepository.findByStudentId(sId);
        if (grades.isEmpty()) {
            throw new NoGradesFoundException("No grades found.");
        }
        Set<Long> taskIds = new HashSet<>();
        int count = 0;
        for (Grade grade : grades) {
            if (grade.isAttendance() && taskIds.add(grade.getTask().getId())) {
                count++;
            }
        }
        return count;
    }
    public List<GradeDTO> getAllStudentGradesFromATask(Long tId, Long sId){
        List<Grade> grades = gradeRepository.findByTaskIdAndStudentId(tId, sId);
        List<GradeDTO> gradeDTOS = new ArrayList<>();
        for (Grade grade : grades){
            gradeDTOS.add(mappingService.convertGradeIntoDTO(grade));
        }
        return gradeDTOS;
    }

    public boolean getAttendanceForTask(Long tId, Long sId){
        List<Grade> grades = gradeRepository.findByTaskIdAndStudentId(tId, sId);
        for (Grade grade : grades){
            if (grade.isAttendance()){
                return true;
            }
        }
        return false;
    }
}
