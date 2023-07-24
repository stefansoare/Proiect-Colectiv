package com.project.pc.controller;

import com.project.pc.dto.StudentDTO;
import com.project.pc.exceptions.IncompleteStudentException;
import com.project.pc.exceptions.NotFoundException;
import com.project.pc.model.Student;
import com.project.pc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/students/")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student){
        try{
            StudentDTO studentDTO = studentService.createStudent(student);
            return new ResponseEntity<>(studentDTO, HttpStatus.OK);
        } catch (IllegalArgumentException | IncompleteStudentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("{tId}/teams/{id}")
    public ResponseEntity<?> addToTeam(@PathVariable("id") Long id, @PathVariable("tId") Long tId){
        try{
            Student student = studentService.addToTeam(id, tId);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        try {
            StudentDTO student = studentService.getStudentById(id);
            return new ResponseEntity<>(student, HttpStatus.FOUND);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("name/{name}")
    public ResponseEntity<List<StudentDTO>> getStudentByName(@PathVariable("name") String name){
        List<StudentDTO> students = studentService.getStudentByName(name);
        return new ResponseEntity<>(students, HttpStatus.FOUND);
    }
    @GetMapping("email/{email}")
    public ResponseEntity<?> getStudentByEmail(@PathVariable("email") String email){
        try {
            StudentDTO student = studentService.getStudentByEmail(email);
            return new ResponseEntity<>(student, HttpStatus.FOUND);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("{tId}")
    public ResponseEntity<List<StudentDTO>> getTeamMembers(@PathVariable("tId") Long tId){
        return new ResponseEntity<>(studentService.getTeamMembers(tId), HttpStatus.OK);
    }
    @GetMapping("activity/{id}")
    public ResponseEntity<List<StudentDTO>> getAllStudentsFromAActivity(@PathVariable("id") Long id){
        if (studentService.getAllStudentsFromAActivity(id) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(studentService.getAllStudentsFromAActivity(id), HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDTO studentDTO){
        try{
            Student student = studentService.updateStudent(id, studentDTO);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch(NotFoundException | IncompleteStudentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PatchMapping("{id}")
    public ResponseEntity<?> patchStudent(@PathVariable("id") Long id, @RequestBody StudentDTO studentDTO) {
        try {
            Student student = studentService.patchStudent(id, studentDTO);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("{id}/teams/{tId}")
    public ResponseEntity<HttpStatus> deleteFromTeam(@PathVariable("id") Long id, @PathVariable("tId") Long tId){
        if (studentService.deleteFromTeam(id, tId) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("email/{email}")
    public ResponseEntity<HttpStatus> deleteStudentByEmail(@PathVariable("email") String email){
        if (studentService.deleteStudentByEmail(email)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("id/{id}")
    public ResponseEntity<HttpStatus> deleteStudentById(@PathVariable("id") Long id){
        if (studentService.deleteStudentById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}