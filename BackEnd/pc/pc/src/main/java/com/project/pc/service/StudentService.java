package com.project.pc.service;

import com.project.pc.exception.CustomException;
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

}
