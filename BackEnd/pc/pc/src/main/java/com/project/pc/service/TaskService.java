package com.project.pc.service;

import com.project.pc.model.Activity;
import com.project.pc.model.Task;
import com.project.pc.repository.ActivityRepository;
import com.project.pc.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ActivityRepository activityRepository;
    public Task createTask(Task task){
        return taskRepository.save(new Task(task.getGrade(), task.getDescription(), task.getDeadline(), task.getAttendance(), task.getComment()));
    }
    public Task addToActivity(Long id, Long aId){
        Task task = taskRepository.findById(id).orElse(null);
        Activity activity = activityRepository.findById(aId).orElse(null);
        if (task == null || activity == null){
            return null;
        }
        task.setActivity(activity);
        taskRepository.save(task);
        return task;
    }
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElse(null);
    }
    public Task updateTask(Long id, Task task){
        Task update = taskRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        update.setGrade(task.getGrade());
        update.setDeadline(task.getDeadline());
        update.setDescription(task.getDescription());
        update.setAttendance(task.getAttendance());
        taskRepository.save(update);
        return update;
    }
    public Task patchTask(long id, Task task) {
        Task update = taskRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        if (task.getAttendance() != 0) {
            update.setAttendance(task.getAttendance());
        }
        if (task.getDescription() != null) {
            update.setDescription(task.getDescription());
        }
        if (task.getGrade() != 0){
            update.setGrade(task.getGrade());
        }
        if (task.getDeadline() != null){
            update.setDeadline(task.getDeadline());
        }
        taskRepository.save(update);
        return update;
    }
    public HttpStatus deleteAllTasks(){
        taskRepository.deleteAll();
        return HttpStatus.OK;
    }
    public HttpStatus deleteTaskById(Long id){
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            taskRepository.deleteById(id);
            return HttpStatus.OK;
        }else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
