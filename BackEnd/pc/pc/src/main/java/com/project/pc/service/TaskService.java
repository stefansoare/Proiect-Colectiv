package com.project.pc.service;

import com.project.pc.dto.TaskDTO;
import com.project.pc.dto.TeamDTO;
import com.project.pc.model.Activity;
import com.project.pc.model.Task;
import com.project.pc.model.Team;
import com.project.pc.repository.ActivityRepository;
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
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private MappingService mappingService;
    public Task createTask(TaskDTO taskDTO){
        if (taskDTO == null)
            return null;
        return taskRepository.save(mappingService.convertDTOIntoTask(taskDTO));
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
    public List<TaskDTO> getAllTasks(){
        List<Task> tasks = taskRepository.findAll();
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for (Task task : tasks){
            taskDTOS.add(mappingService.convertTaskIntoDTO(task));
        }
        return taskDTOS;
    }
    public TaskDTO getTaskById(Long id){
        return mappingService.convertTaskIntoDTO(taskRepository.findById(id).orElse(null));
    }
    public Task updateTask(Long id, TaskDTO taskDTO){
        Task update = taskRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        update.setGrade(taskDTO.getGrade());
        update.setDeadline(taskDTO.getDeadline());
        update.setDescription(taskDTO.getDescription());
        update.setAttendance(taskDTO.getAttendance());
        update.setComment(taskDTO.getComment());
        update.setActivity(mappingService.convertDTOIntoActivity(taskDTO.getActivityDTO()));
        taskRepository.save(update);
        return update;
    }
    public Task patchTask(long id, TaskDTO taskDTO) {
        Task update = taskRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        if (taskDTO.getAttendance() != 0) {
            update.setAttendance(taskDTO.getAttendance());
        }
        if (taskDTO.getDescription() != null) {
            update.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getGrade() != 0){
            update.setGrade(taskDTO.getGrade());
        }
        if (taskDTO.getDeadline() != null){
            update.setDeadline(taskDTO.getDeadline());
        }
        if (taskDTO.getComment() != null) {
            update.setComment(taskDTO.getComment());
        }
        if (taskDTO.getActivityDTO() != null){
            update.setActivity(mappingService.convertDTOIntoActivity(taskDTO.getActivityDTO()));
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
