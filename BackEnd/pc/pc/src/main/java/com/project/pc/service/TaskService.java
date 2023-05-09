package com.project.pc.service;

import com.project.pc.exception.CustomException;
import com.project.pc.model.Student;
import com.project.pc.model.Task;
import com.project.pc.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    public Task createTask(Task newTask){
        return taskRepository.save(new Task(newTask.getGrade(), newTask.getDescription(), newTask.getDeadline(), newTask.getAttendance()));
    }
    public List<Task> getAllTasks(){
        List<Task> allTasks = new ArrayList<>();
        taskRepository.findAll().forEach(allTasks::add);
        return allTasks;
    }
    public Task getTaskById(Long id){
        Optional<Task> task = taskRepository.findById(id);
        return task.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"There is no task with id : " + id));
    }
}
