package com.project.pc.controller;

import com.project.pc.model.Task;
import com.project.pc.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @PostMapping("/task")
    public ResponseEntity<Task> createTask(@RequestBody Task newTask){
        Task createdTask = taskService.createTask(newTask);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
    @GetMapping("/task")
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tasks, HttpStatus.FOUND);
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id){
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.FOUND);
    }
    @PutMapping("/task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task task){
        return new ResponseEntity<>(taskService.updateTask(id, task));
    }
    @DeleteMapping("/task")
    public ResponseEntity<HttpStatus> deleteAllTasks(){
        return new ResponseEntity<>(taskService.deleteAllTasks());
    }
    @DeleteMapping("/task/{id}")
    public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable("id") Long id){
        return new ResponseEntity<>(taskService.deleteTaskById(id));
    }
}
