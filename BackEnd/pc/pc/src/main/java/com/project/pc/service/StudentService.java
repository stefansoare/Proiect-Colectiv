package com.project.pc.service;

import com.project.pc.dto.StudentDTO;
import com.project.pc.model.Status;
import com.project.pc.model.Student;
import com.project.pc.model.Team;
import com.project.pc.repository.StatusRepository;
import com.project.pc.repository.StudentRepository;
import com.project.pc.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private StatusRepository statusRepository;
    @Autowired
    private MappingService mappingService;
    public StudentDTO createStudent(Student student){
        if (student == null)
            return null;
        Status status = new Status();
        statusRepository.save(status);
        student.setStatus(status);
        studentRepository.save(student);
        return mappingService.convertStudentIntoDTO(student);
    }
    public Student addToTeam(Long id, Long tId){
        Student student = studentRepository.findById(id).orElse(null);
        Team team = teamRepository.findById(tId).orElse(null);
        if (student == null || team == null){
            return null;
        }
        Status status = statusRepository.findById(student.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        student.setTeam(team);
        student.setStatus(status);
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
    public List<StudentDTO> getAllStudentsFromAActivity(Long aId){
        List<Team> teams = teamRepository.findByActivityId(aId);
        if (teams.isEmpty()){
            return null;
        }
        List<StudentDTO> students = new ArrayList<>();
        for (Team team : teams){
            List<StudentDTO> studentDTOS = getTeamMembers(team.getId());
            for (StudentDTO studentDTO : studentDTOS){
                students.add(studentDTO);
            }
        }
        return students;
    }
    public Student updateStudent (Long id, StudentDTO studentDTO){
        Student update = studentRepository.findStudentById(id).orElse(null);
        if (update == null){
            return null;
        }
        Status status = statusRepository.findById(update.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        update.setName(studentDTO.getName());
        update.setEmail(studentDTO.getEmail());
        update.setStatus(status);
        studentRepository.save(update);
        return update;
    }
    public Student patchStudent(long id, StudentDTO studentDTO) {
        Student update = studentRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        Status status = statusRepository.findById(update.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        if (studentDTO.getName() != null) {
            update.setName(studentDTO.getName());
        }
        if (studentDTO.getEmail() != null) {
            update.setEmail(studentDTO.getEmail());
        }
        update.setStatus(status);
        studentRepository.save(update);
        return update;
    }
    public boolean deleteFromTeam(Long id, Long tId){
        Student student = studentRepository.findStudentById(id).orElse(null);
        if (student == null ||student.getTeam() == null || student.getTeam().getId() != tId){
            return false;
        }
        student.setTeam(null);
        studentRepository.save(student);
        return true;
    }
    public boolean deleteStudentByEmail(String email){
        Optional<Student> student = studentRepository.findStudentByEmail(email);
        if (student.isPresent()){
            studentRepository.deleteById(student.get().getId());
            return true;
        }
        return false;
    }
    public boolean deleteStudentById(long id){
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()){
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

}