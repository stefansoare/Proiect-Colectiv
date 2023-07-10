package com.project.pc.service;

import com.project.pc.dto.TaskDTO;
import com.project.pc.model.Activity;
import com.project.pc.model.Status;
import com.project.pc.model.Student;
import com.project.pc.model.Task;
import com.project.pc.repository.ActivityRepository;
import com.project.pc.repository.StatusRepository;
import com.project.pc.repository.StudentRepository;
import com.project.pc.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private StudentRepository studentRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private MappingService mappingService;
    public TaskDTO createTask(Task task){
        if (task == null)
            return null;
        Status status = new Status();
        statusRepository.save(status);
        task.setStatus(status);
        taskRepository.save(task);
        return mappingService.convertTaskIntoDTO(task);
    }
    public Task addToActivity(Long id, Long aId){
        Task task = taskRepository.findById(id).orElse(null);
        Activity activity = activityRepository.findById(aId).orElse(null);
        if (task == null || activity == null){
            return null;
        }
        Status status = statusRepository.findById(task.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
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
    public List<TaskDTO> getAllTasksFromActivity(Long aId){
        List<Task> tasks = taskRepository.findByActivityId(aId);
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for (Task task : tasks){
            taskDTOS.add(mappingService.convertTaskIntoDTO(task));
        }
        return taskDTOS;
    }
    public Task updateTask(Long id, TaskDTO taskDTO){
        Task update = taskRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        Status status = statusRepository.findById(update.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        update.setId(taskDTO.getId());
        update.setDeadline(taskDTO.getDeadline());
        update.setDescription(taskDTO.getDescription());
        update.setStatus(status);
        taskRepository.save(update);
        return update;
    }
    public Task patchTask(long id, TaskDTO taskDTO) {
        Task update = taskRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        Status status = statusRepository.findById(update.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        if (taskDTO.getId() != 0) {
            update.setId(taskDTO.getId());
        }
        if (taskDTO.getDescription() != null) {
            update.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getDeadline() != null){
            update.setDeadline(taskDTO.getDeadline());
        }
        update.setStatus(status);
        taskRepository.save(update);
        return update;
    }
    public boolean deleteTaskById(Long id){
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}