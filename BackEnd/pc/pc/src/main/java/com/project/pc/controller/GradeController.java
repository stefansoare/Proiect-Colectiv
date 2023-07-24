package com.project.pc.controller;

import com.project.pc.dto.GradeDTO;
import com.project.pc.exceptions.NoGradesFoundException;
import com.project.pc.exceptions.NotFoundException;
import com.project.pc.model.Grade;
import com.project.pc.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/grades/")
public class GradeController {
    @Autowired
    private GradeService gradeService;
    @PostMapping("from/{mId}/to/{sId}/for/{tId}")
    public ResponseEntity<?> giveGrade(@PathVariable("mId") Long mId, @PathVariable("sId") Long sId, @PathVariable("tId") Long tId, @RequestBody Grade grade){
        try {
            GradeDTO gradeDTO = gradeService.giveGrade(mId, sId, tId, grade);
            return new ResponseEntity<>(gradeDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("mean/{tId}/for/{sId}")
    public ResponseEntity<?> getStudentGradesMean(@PathVariable("tId") Long tId, @PathVariable("sId") Long sId){
        try {
            Long mean = gradeService.getStudentGradesMean(tId, sId);
            return new ResponseEntity<>(mean, HttpStatus.OK);
        } catch (NoGradesFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("attendance/{sId}")
    public ResponseEntity<?> getAllStudentAttendances(@PathVariable("sId") Long sId){
        try {
            Integer att = gradeService.getAllStudentAttendances(sId);
            return new ResponseEntity<>(att, HttpStatus.OK);
        } catch (NoGradesFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("grades/{tId}/for/{sId}")
    public ResponseEntity<List<GradeDTO>> getAllStudentGradesFromATask(@PathVariable("tId") Long tId, @PathVariable("sId") Long sId){
        return new ResponseEntity(gradeService.getAllStudentGradesFromATask(tId, sId), HttpStatus.OK);
    }
    @GetMapping("task/{tId}/attendance/student/{sId}")
    public ResponseEntity<Boolean> getAttendanceForTask(@PathVariable("tId") Long tId, @PathVariable("sId") Long sId){
        return new ResponseEntity<>(gradeService.getAttendanceForTask(tId, sId), HttpStatus.OK);
    }
}
