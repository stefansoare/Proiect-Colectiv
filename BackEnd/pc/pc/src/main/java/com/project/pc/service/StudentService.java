package com.project.pc.service;

import com.project.pc.model.Student;
import com.project.pc.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    public Student createStudent(Student student){
        return studentRepository.save(new Student(student.getName(), student.getEmail(), student.getLeader()));
    }
}
