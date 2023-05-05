package com.project.pc.service;

import com.project.pc.exception.CustomException;
import com.project.pc.model.Activity;
import com.project.pc.model.Student;
import com.project.pc.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    public Student createStudent(Student student){
        return studentRepository.save(new Student(student.getName(), student.getEmail(), student.getLeader()));
    }
    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    public Student getStudentById (Long id) {
        Optional<Student> studentOptional =  studentRepository.findById(id);
        return studentOptional.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"There is no student with id : " + id));
    }
    public Student updateStudent(Long id, Student newStudent){
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null){
            return null;
        }
        student.setEmail(newStudent.getEmail());
        student.setLeader(newStudent.getLeader());
        student.setName(newStudent.getName());
        return student;
    }
    public HttpStatus deleteAllStudents(){
        studentRepository.deleteAll();
        return HttpStatus.OK;
    }
    public HttpStatus deleteStudentById(long id){
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()){
            studentRepository.deleteById(id);
            return HttpStatus.OK;
        }else {
            return HttpStatus.NOT_FOUND;
        }
    }

}
