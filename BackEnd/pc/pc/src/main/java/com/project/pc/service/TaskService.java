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
import java.util.Objects;
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
    public HttpStatus updateTask(Long id, Task task){
        Task task1 = taskRepository.findById(id).orElse(null);
        if (task1 == null){
            return HttpStatus.NOT_FOUND;
        }
        if (Objects.nonNull(task.getGrade())) {
            task1.setGrade(task.getGrade());
        }
        if (Objects.nonNull(task.getDescription())) {
            task1.setDescription(task.getDescription());
        }
        if (Objects.nonNull(task.getDeadline())) {
            task1.setDeadline(task.getDeadline());
        }
        if (Objects.nonNull(task.getAttendance())) {
            task1.setAttendance(task.getAttendance());
        }

        taskRepository.save(task1);
        return HttpStatus.OK;
    }
    public HttpStatus deleteAllTasks(){
        taskRepository.deleteAll();
        return HttpStatus.OK;
    }
    public HttpStatus deleteTaskById(long id){
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            taskRepository.deleteById(id);
            return HttpStatus.OK;
        }else {
            return HttpStatus.NOT_FOUND;
        }
    }
}
