package com.project.pc.controller;

import com.project.pc.dto.TaskDTO;
import com.project.pc.exceptions.IncompleteTaskException;
import com.project.pc.exceptions.NotFoundException;
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
    public ResponseEntity<?> createTask(@RequestBody Task task){
        try{
            TaskDTO taskDTO = taskService.createTask(task);
            return new ResponseEntity<>(taskDTO, HttpStatus.OK);
        } catch (IllegalArgumentException | IncompleteTaskException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("{id}/activities/{aId}")
    public ResponseEntity<?> addToActivity(@PathVariable("id") Long id, @PathVariable("aId") Long aId){
        try {
            Task task = taskService.addToActivity(id, aId);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(){
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getTaskById(@PathVariable("id") Long id){
        try {
            TaskDTO task = taskService.getTaskById(id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("activities/{aId}")
    public ResponseEntity<List<TaskDTO>> getAllTasksFromActivity(@PathVariable("aId") Long aId){
        return new ResponseEntity<>(taskService.getAllTasksFromActivity(aId), HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO){
        try {
            Task update = taskService.updateTask(id, taskDTO);
            return new ResponseEntity<>(update, HttpStatus.OK);
        } catch (NotFoundException | IncompleteTaskException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PatchMapping("{id}")
    public ResponseEntity<?> patchTask(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO) {
        try {
            Task updated = taskService.patchTask(id, taskDTO);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable("id") Long id){
        if (taskService.deleteTaskById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}