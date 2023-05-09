package com.project.pc.controller;

import com.project.pc.model.Task;
import com.project.pc.repository.TaskRepository;
import com.project.pc.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;
    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody Task newTask){
        Task createdTask = taskService.createTask(newTask);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
}
