package com.project.pc.service;

import com.project.pc.dto.StudentDTO;
import com.project.pc.model.Student;
import com.project.pc.model.Team;
import com.project.pc.repository.StudentRepository;
import com.project.pc.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MappingService mappingService;
    public Student createStudent(StudentDTO studentDTO){
        if (studentDTO == null)
            return null;
        return studentRepository.save(mappingService.convertDTOIntoStudent(studentDTO));
    }
    public Student addToTeam(Long id, Long tId){
        Student student = studentRepository.findById(id).orElse(null);
        Team team = teamRepository.findById(tId).orElse(null);
        if (student == null || team == null){
            return null;
        }
        student.setTeam(team);
        studentRepository.save(student);
        return student;
    }
    public List<StudentDTO> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students){
            studentDTOS.add(mappingService.convertStudentIntoDTO(student));
        }
        return studentDTOS;
    }
    public StudentDTO getStudentById (Long id) {
        return mappingService.convertStudentIntoDTO(studentRepository.findStudentById(id).orElse(null));
    }
    public List<StudentDTO> getStudentByName(String name){
        List<Student> students =  studentRepository.findStudentByName(name);
        List<StudentDTO> studentsByName = new ArrayList<>();
        for (Student student : students){
            studentsByName.add(mappingService.convertStudentIntoDTO(student));
        }
        return studentsByName;
    }
    public StudentDTO getStudentByEmail(String email){
        return mappingService.convertStudentIntoDTO(studentRepository.findStudentByEmail(email).orElse(null));
    }
    public List<StudentDTO> getTeamMembers(Long tId){
        List<Student> students = studentRepository.findByTeamId(tId);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students){
            studentDTOS.add(mappingService.convertStudentIntoDTO(student));
        }
        return studentDTOS;
    }
    public Student updateStudent (Long id, StudentDTO studentDTO){
        Student update = studentRepository.findStudentById(id).orElse(null);
        if (update == null){
            return null;
        }
        update.setName(studentDTO.getName());
        update.setEmail(studentDTO.getEmail());
        studentRepository.save(update);
        return update;
    }
    public Student patchStudent(long id, StudentDTO studentDTO) {
        Student update = studentRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        if (studentDTO.getName() != null) {
            update.setName(studentDTO.getName());
        }
        if (studentDTO.getEmail() != null) {
            update.setEmail(studentDTO.getEmail());
        }
        studentRepository.save(update);
        return update;
    }
    public HttpStatus deleteFromTeam(Long id, Long tId){
        Student student = studentRepository.findStudentById(id).orElse(null);
        if (student == null ||student.getTeam() == null || student.getTeam().getId() != tId){
            return HttpStatus.BAD_REQUEST;
        }
        student.setTeam(null);
        studentRepository.save(student);
        return HttpStatus.OK;
    }
    public HttpStatus deleteAllStudents(){
        studentRepository.deleteAll();
        return HttpStatus.OK;
    }
    public HttpStatus deleteStudentByEmail(String email){
        Optional<Student> student = studentRepository.findStudentByEmail(email);
        if (student.isPresent()){
            studentRepository.deleteById(student.get().getId());
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }
    public HttpStatus deleteStudentById(long id){
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()){
            studentRepository.deleteById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

}
