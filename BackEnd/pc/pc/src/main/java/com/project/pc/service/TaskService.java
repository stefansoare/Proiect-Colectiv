package com.project.pc.service;

import com.project.pc.dto.StudentDTO;
import com.project.pc.dto.TaskDTO;
import com.project.pc.model.Activity;
import com.project.pc.model.Student;
import com.project.pc.model.Task;
import com.project.pc.model.Team;
import com.project.pc.repository.ActivityRepository;
import com.project.pc.repository.StudentRepository;
import com.project.pc.repository.TaskRepository;
import com.project.pc.repository.TeamRepository;
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
    private StudentRepository studentRepository;
    @Autowired
    private TeamRepository teamRepository;
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
    public Task assignTaskToStudent(Long sId, Long tId){
        Task task = taskRepository.findById(tId).orElse(null);
        Student student = studentRepository.findStudentById(sId).orElse(null);
        if (task == null || student == null){
            return null;
        }
        if (task.getStudent() == null){
            task.setStudent(student);
            taskRepository.save(task);
            return task;
        }
        Task newTask = new Task();
        newTask.setDescription(task.getDescription());
        newTask.setDeadline(task.getDeadline());
        newTask.setActivity(task.getActivity());
        newTask.setStudent(student);
        taskRepository.save(newTask);
        return newTask;
    }
    public Task assignTaskToTeam(Long teamId, Long tId){
        Task task = taskRepository.findById(tId).orElse(null);
        Team team = teamRepository.findById(teamId).orElse(null);
        if (task == null || team == null){
            return null;
        }
        if (task.getTeam() == null){
            task.setTeam(team);
            taskRepository.save(task);
            return task;
        }
        Task newTask = new Task();
        newTask.setDescription(task.getDescription());
        newTask.setDeadline(task.getDeadline());
        newTask.setActivity(task.getActivity());
        newTask.setTeam(team);
        taskRepository.save(newTask);
        return newTask;
    }
    public List<TaskDTO> getAllTasks(){
        List<Task> tasks = taskRepository.findAll();
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for (Task task : tasks){
            taskDTOS.add(mappingService.convertTaskIntoDTO(task));
        }
        return taskDTOS;
    }
    public List<TaskDTO> getActivityTasks(Long aId){
        List<Task> tasks = taskRepository.findByActivityId(aId);
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
    public Integer addGrades(List<Task> tasks){
        if (tasks.isEmpty()){
            return 0;
        }
        int sum = 0;
        for(Task task : tasks){
            sum = sum + task.getGrade();
        }
        return sum / tasks.size();
    }
    public Integer getTeamStats(Long tId){
        List<Student> students = studentRepository.findByTeamId(tId);
        if (students.isEmpty()){
            return 0;
        }
        int sum = 0;
        for (Student student : students){
            List<Task> tasks = taskRepository.findByStudentId(student.getId());
            sum = sum + addGrades(tasks);
        }
        return sum / students.size();
    }
    public Integer getAllStudentAttendance(Long sId){
        List<Task> tasks = taskRepository.findByStudentId(sId);
        int sum = 0;
        for (Task task : tasks){
            sum = sum + task.getAttendance();
        }
        return sum;
    }
    public List<Task> getAllTasksOfAStudent(Long sId){
        return taskRepository.findByStudentId(sId);
    }
    public List<Task> getAllTasksOfAStudentByEmail(String email){
        Student student = studentRepository.findStudentByEmail(email).orElse(null);
        if (student == null){
            return null;
        }
        return getAllTasksOfAStudent(student.getId());
    }
    public List<Task> getAllTasksOfAStudentByName(String name){
        List<Student> students = studentRepository.findStudentByName(name);
        if (students.isEmpty()){
            return null;
        }
        List<Task> allTasks = new ArrayList<>();
        for (Student student : students){
            List<Task> tasks = getAllTasksOfAStudent(student.getId());
            for (Task task : tasks){
                allTasks.add(task);
            }
        }
        return allTasks;
    }
    public Task updateTask(Long id, TaskDTO taskDTO){
        Task update = taskRepository.findById(id).orElse(null);
        if (update == null){
            return null;
        }
        update.setId(taskDTO.getId());
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
        if (taskDTO.getId() != 0) {
            update.setId(taskDTO.getId());
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
    public Task gradeStudent(Long sId, Long tId, int grade){
        List<Task> tasks = taskRepository.findByStudentId(sId);
        if (tasks.isEmpty()){
            return null;
        }
        for (Task task : tasks){
            if (task.getId() == tId){
                task.setGrade(grade);
                taskRepository.save(task);
                return task;
            }
        }
        return null;
    }
    public Task putAttendanceStudent(Long sId, Long tId, int attendance){
        List<Task> tasks = taskRepository.findByStudentId(sId);
        if (tasks.isEmpty()){
            return null;
        }
        for (Task task : tasks){
            if (task.getId() == tId){
                task.setAttendance(attendance);
                taskRepository.save(task);
                return task;
            }
        }
        return null;
    }
    public Task commentStudent(Long sId, Long tId, String comment){
        List<Task> tasks = taskRepository.findByStudentId(sId);
        if (tasks.isEmpty()){
            return null;
        }
        for (Task task : tasks){
            if (task.getId() == tId){
                task.setComment(comment);
                taskRepository.save(task);
                return task;
            }
        }
        return null;
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
