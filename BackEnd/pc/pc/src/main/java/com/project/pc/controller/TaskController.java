package com.project.pc.controller;

import com.project.pc.dto.TaskDTO;
import com.project.pc.model.Task;
import com.project.pc.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/tasks/")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO){
        return new ResponseEntity<>(taskService.createTask(taskDTO), HttpStatus.CREATED);
    }
    @PostMapping("{id}/activities/{aId}")
    public ResponseEntity<Task> addToActivity(@PathVariable("id") Long id, @PathVariable("aId") Long aId){
        Task task = taskService.addToActivity(id, aId);
        if (task == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @PostMapping("{sId}/studenttasks/{tId}")
    public ResponseEntity<Task> assignTaskToStudent(@PathVariable("sId") Long sId, @PathVariable("tId") Long tId){
        Task task = taskService.assignTaskToStudent(sId, tId);
        if (task == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @PostMapping("{teamId}/teamtasks/{tId}")
    public ResponseEntity<Task> assignTaskToTeam(@PathVariable("teamId") Long teamId, @PathVariable("tId") Long tId){
        Task task = taskService.assignTaskToTeam(teamId, tId);
        if (task == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(){
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") Long id){
        TaskDTO task = taskService.getTaskById(id);
        if (task == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @GetMapping("{id}/attendance")
    public ResponseEntity<Integer> getAttendance(@PathVariable("id") Long id) {
        TaskDTO task = taskService.getTaskById(id);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(task.getAttendance(), HttpStatus.OK);
    }
    @GetMapping("{id}/grade")
    public ResponseEntity<Integer> getGrade(@PathVariable("id") Long id) {
        TaskDTO task = taskService.getTaskById(id);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(task.getGrade(), HttpStatus.OK);
    }
    @GetMapping("activities/{aId}")
    public ResponseEntity<List<TaskDTO>> getAllTasksFromActivity(@PathVariable("aId") Long aId){
        return new ResponseEntity<>(taskService.getAllTasksFromActivity(aId), HttpStatus.OK);
    }
    @GetMapping("stats/{tId}")
    public ResponseEntity<Integer> getTeamStats(@PathVariable("tId") Long tId){
        return new ResponseEntity<>(taskService.getTeamStats(tId), HttpStatus.OK);
    }
    @GetMapping("attendances/{sId}")
    public ResponseEntity<Integer> getStudentAttendances(@PathVariable("sId") Long sId){
        return new ResponseEntity<>(taskService.getAllStudentAttendance(sId), HttpStatus.OK);
    }
    @GetMapping("{sId}/alltasks")
    public ResponseEntity<List<Task>> getAllTasksOfAStudent(@PathVariable("sId") Long sId){
        return new ResponseEntity<>(taskService.getAllTasksOfAStudent(sId), HttpStatus.OK);
    }
    @GetMapping("email/{sEmail}/alltasks")
    public ResponseEntity<List<Task>> getAllTasksOfAStudentByEmail(@PathVariable("sEmail") String sEmail){
        if (taskService.getAllTasksOfAStudentByEmail(sEmail) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(taskService.getAllTasksOfAStudentByEmail(sEmail), HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO){
        Task update = taskService.updateTask(id, taskDTO);
        if (update == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
    @PatchMapping("{id}")
    public ResponseEntity<Task> patchTask(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO) {
        Task updated = taskService.patchTask(id, taskDTO);
        if (updated == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    @PatchMapping("{sId}/task/{tId}/grade")
    public ResponseEntity<Task> gradeStudent(@PathVariable("sId") Long sId, @PathVariable("tId") Long tId, @RequestBody TaskDTO taskDTO){
        Task task = taskService.gradeStudent(sId, tId, taskDTO.getGrade());
        if (task == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @PatchMapping("{sId}/task/{tId}/attendance")
    public ResponseEntity<Task> putAttendanceStudent(@PathVariable("sId") Long sId, @PathVariable("tId") Long tId, @RequestBody TaskDTO taskDTO){
        Task task = taskService.putAttendanceStudent(sId, tId, taskDTO.getAttendance());
        if (task == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @PatchMapping("{sId}/task/{tId}/comment")
    public ResponseEntity<Task> commentStudent(@PathVariable("sId") Long sId, @PathVariable("tId") Long tId, @RequestBody TaskDTO taskDTO){
        Task task = taskService.commentStudent(sId, tId, taskDTO.getComment());
        if (task == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllTasks(){
        return new ResponseEntity<>(taskService.deleteAllTasks());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable("id") Long id){
        return new ResponseEntity<>(taskService.deleteTaskById(id));
    }
}
