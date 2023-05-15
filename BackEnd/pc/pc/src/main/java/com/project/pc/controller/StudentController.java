package com.project.pc.controller;

import com.project.pc.model.Student;
import com.project.pc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/students/")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }
    @PostMapping("{id}/teams/{tId}")
    public ResponseEntity<Student> addToTeam(@PathVariable("id") Long id, @PathVariable("tId") Long tId){
        Student student = studentService.addToTeam(id, tId);
        if (student == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(student, HttpStatus.FOUND);
    }
    @GetMapping("name/{name}")
    public ResponseEntity<List<Student>> getStudentByName(@PathVariable("name") String name){
        List<Student> students = studentService.getStudentByName(name);
        return new ResponseEntity<>(students, HttpStatus.FOUND);
    }
    @GetMapping("email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable("email") String email){
        Student student = studentService.getStudentByEmail(email);
        if (student == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    @GetMapping("{tId}")
    public ResponseEntity<List<Student>> getTeamMembers(@PathVariable("tId") Long tId){
        return new ResponseEntity<>(studentService.getTeamMembers(tId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student student){
        Student update = studentService.updateStudent(id, student);
        if (update == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
    @PatchMapping("{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        Student updated = studentService.patchStudent(id, student);
        if (updated == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    @DeleteMapping("{id}/teams/{tId}")
    public ResponseEntity<HttpStatus> deleteFromTeam(@PathVariable("id") Long id, @PathVariable("tId") Long tId){
        return new ResponseEntity<>(studentService.deleteFromTeam(id, tId));
    }
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllStudents(){
        return new ResponseEntity<>(studentService.deleteAllStudents());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteStudentById(@PathVariable("id") Long id){
        return new ResponseEntity<>(studentService.deleteStudentById(id));
    }
}
