package com.project.pc.service;

import com.project.pc.model.Task;
import com.project.pc.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    public Task createTask(Task newTask){
        return taskRepository.save(new Task(newTask.getGrade(), newTask.getDescription(), newTask.getDeadline(), newTask.getAttendance()));
    }
}
