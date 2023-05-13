package com.project.pc.controller;

import com.project.pc.model.Student;
import com.project.pc.repository.StudentRepository;
import com.project.pc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    private Long id;

    @PostMapping("/student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }
    @GetMapping("/student")
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/student/{studId}")
    public Student getStudent(@PathVariable("studId") Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/student/{studId}")
    public ResponseEntity<Student> updateStudent(@PathVariable("studId") Long studId, @RequestBody Student newStudent){
        Student student = studentService.updateStudent(studId, newStudent);
        if (student == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
    }
    @DeleteMapping("/student")
    public ResponseEntity<HttpStatus> deleteAllStudents(){
        return new ResponseEntity<>(studentService.deleteAllStudents());
    }
    @DeleteMapping("/student/{studId}")
    public ResponseEntity<HttpStatus> deleteStudentById(@PathVariable("studId") Long studId){
        return new ResponseEntity<>(studentService.deleteStudentById(studId));
    }
}
