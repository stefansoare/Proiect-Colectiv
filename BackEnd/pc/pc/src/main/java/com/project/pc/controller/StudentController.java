package com.project.pc.controller;

import com.project.pc.dto.StudentDTO;
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
    public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO){
        Student createdStudent = studentService.createStudent(studentDTO);
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
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("id") Long id) {
        StudentDTO student = studentService.getStudentById(id);
        if (student == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(student, HttpStatus.FOUND);
    }
    @GetMapping("name/{name}")
    public ResponseEntity<List<StudentDTO>> getStudentByName(@PathVariable("name") String name){
        List<StudentDTO> students = studentService.getStudentByName(name);
        return new ResponseEntity<>(students, HttpStatus.FOUND);
    }
    @GetMapping("email/{email}")
    public ResponseEntity<StudentDTO> getStudentByEmail(@PathVariable("email") String email){
        StudentDTO student = studentService.getStudentByEmail(email);
        if (student == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    @GetMapping("{tId}")
    public ResponseEntity<List<StudentDTO>> getTeamMembers(@PathVariable("tId") Long tId){
        return new ResponseEntity<>(studentService.getTeamMembers(tId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDTO studentDTO){
        Student update = studentService.updateStudent(id, studentDTO);
        if (update == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
    @PatchMapping("{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable("id") Long id, @RequestBody StudentDTO studentDTO) {
        Student updated = studentService.patchStudent(id, studentDTO);
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
    @DeleteMapping("email/{email}")
    public ResponseEntity<HttpStatus> deleteStudentByEmail(@PathVariable("email") String email){
        return new ResponseEntity<>(studentService.deleteStudentByEmail(email));
    }
    @DeleteMapping("id/{id}")
    public ResponseEntity<HttpStatus> deleteStudentById(@PathVariable("id") Long id){
        return new ResponseEntity<>(studentService.deleteStudentById(id));
    }
}
