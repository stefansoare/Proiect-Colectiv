package com.project.pc.controller;

import com.project.pc.model.Grade;
import com.project.pc.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/grades/")
public class GradeController {
    @Autowired
    private GradeService gradeService;
    @PostMapping("from/{mId}/to/{sId}/for/{tId}")       // request body
    public ResponseEntity giveGrade(@PathVariable("mId") Long mId, @PathVariable("sId") Long sId, @PathVariable("tId") Long tId, @RequestBody Grade grade){
        if (gradeService.giveGrade(mId, sId, tId, grade) == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(gradeService.giveGrade(mId, sId, tId, grade), HttpStatus.OK);
    }
    @GetMapping("mean/{tId}/for/{sId}")
    public ResponseEntity getStudentGradesMean(@PathVariable("tId") Long tId, @PathVariable("sId") Long sId){
        if (gradeService.getStudentGradesMean(tId, sId) == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(gradeService.getStudentGradesMean(tId, sId), HttpStatus.OK);
    }
    @GetMapping("attendance/{sId}")
    public ResponseEntity getAllStudentAttendances(@PathVariable("sId") Long sId){ // get only once per task
        if (gradeService.getAllStudentAttendances(sId) == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(gradeService.getAllStudentAttendances(sId), HttpStatus.OK);
    }
    @GetMapping("grades/{tId}/for/{sId}")
    public ResponseEntity getAllStudentGradesFromATask(@PathVariable("tId") Long tId, @PathVariable("sId") Long sId){
        if (gradeService.getAllStudentGradesFromATask(tId, sId) == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(gradeService.getAllStudentGradesFromATask(tId, sId), HttpStatus.OK);
    }
}
