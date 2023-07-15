package com.project.pc.serviceTest;

import com.project.pc.model.Activity;
import com.project.pc.model.Task;
import com.project.pc.model.Status;
import com.project.pc.repository.ActivityRepository;
import com.project.pc.repository.StatusRepository;
import com.project.pc.repository.TaskRepository;
import com.project.pc.service.TaskService;
import com.project.pc.service.MappingService;
import com.project.pc.dto.TaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Rollback
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private MappingService mappingService;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask_ValidTask_CreatesTaskAndReturnsDTO() {
        Task task = new Task();
        Status status = new Status();
        doReturn(status).when(statusRepository).save(any(Status.class));
        when(taskRepository.save(task)).thenReturn(task);
        when(mappingService.convertTaskIntoDTO(task)).thenReturn(new TaskDTO());

        TaskDTO result = taskService.createTask(task);

        assertNotNull(result);
        assertEquals(new TaskDTO(), result);

        verify(taskRepository).save(task);
        verify(statusRepository).save(any(Status.class));
        verify(mappingService).convertTaskIntoDTO(task);
        verifyNoMoreInteractions(taskRepository);
        verifyNoMoreInteractions(statusRepository);
        verifyNoMoreInteractions(mappingService);
    }


    @Test
    void testCreateTask_NullTask_ReturnsNull() {
        Task task = null;

        TaskDTO result = taskService.createTask(task);

        assertEquals(null, result);

        verifyNoInteractions(taskRepository);
        verifyNoInteractions(mappingService);
    }

    @Test
    void testAddToActivity() {
        Long taskId = 1L;
        Long activityId = 2L;
        Task task = new Task();
        task.setId(taskId);
        Activity activity = new Activity();
        activity.setId(activityId);
        Status status = new Status();
        status.setId(3L);
        task.setStatus(status);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));
        when(statusRepository.findById(status.getId())).thenReturn(Optional.of(status));

        Task result = taskService.addToActivity(taskId, activityId);


        assertNotNull(result);
        assertEquals(activity, task.getActivity());
        verify(taskRepository, times(1)).save(task);
        verify(statusRepository, times(1)).save(status);
        verifyNoMoreInteractions(taskRepository);
        verifyNoMoreInteractions(statusRepository);
    }

    @Test
    void testGetAllTasks_RepositoryReturnsTasks_ReturnsTaskDTOList() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());

        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDTO> result = taskService.getAllTasks();

        assertEquals(tasks.size(), result.size());

        verify(taskRepository).findAll();
        verify(mappingService, times(tasks.size())).convertTaskIntoDTO(any(Task.class));
        verifyNoMoreInteractions(taskRepository);
        verifyNoMoreInteractions(mappingService);
    }

    @Test
    void testGetAllTasks_RepositoryReturnsEmptyList_ReturnsEmptyList() {
        List<Task> tasks = new ArrayList<>();

        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDTO> result = taskService.getAllTasks();

        assertTrue(result.isEmpty());

        verify(taskRepository).findAll();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void testGetTaskById_ValidId_ReturnsTaskDTO() {
        Task task = new Task(1L);
        Long taskId = task.getId();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(mappingService.convertTaskIntoDTO(task)).thenReturn(new TaskDTO());

        TaskDTO result = taskService.getTaskById(taskId);

        assertNotNull(result);

        verify(taskRepository).findById(taskId);
        verify(mappingService).convertTaskIntoDTO(task);
        verifyNoMoreInteractions(taskRepository);
        verifyNoMoreInteractions(mappingService);
    }

    @Test
    void testGetTaskById_InvalidId_ReturnsNull() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskDTO result = taskService.getTaskById(taskId);

        assertNull(result);

        verify(taskRepository).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void testGetAllTasksFromActivity_RepositoryReturnsTasksByActivityId_ReturnsTaskDTOList() {
        List<Task> tasks = new ArrayList<>();
        Long activityId = 2L;
        tasks.add(new Task(1L, new Activity(2L)));
        tasks.add(new Task(2L, new Activity(3L)));
        tasks.add(new Task(3L, new Activity(2L)));

        when(taskRepository.findByActivityId(activityId)).thenReturn(tasks);

        List<TaskDTO> result = taskService.getAllTasksFromActivity(activityId);

        assertEquals(tasks.size(), result.size());

        verify(taskRepository).findByActivityId(activityId);
        verify(mappingService, times(tasks.size())).convertTaskIntoDTO(any(Task.class));
        verifyNoMoreInteractions(taskRepository);
        verifyNoMoreInteractions(mappingService);
    }

    @Test
    void testGetAllTasksFromActivity_RepositoryReturnsEmptyList_ReturnsEmptyList() {
        List<Task> tasks = new ArrayList<>();
        Long activityId = 2L;

        when(taskRepository.findByActivityId(activityId)).thenReturn(tasks);

        List<TaskDTO> result = taskService.getAllTasksFromActivity(activityId);

        assertTrue(result.isEmpty());

        verify(taskRepository).findByActivityId(activityId);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void testUpdateTask_TaskExistsAndStatusExists_TaskUpdatedAndStatusSaved() {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setDescription("Updated description");
        taskDTO.setDeadline("2023-12-31");

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setDescription("Original description");
        existingTask.setDeadline("2023-12-31");
        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingTask.setStatus(existingStatus);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(statusRepository.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));

        Task result = taskService.updateTask(taskId, taskDTO);

        assertEquals(existingTask, result);
        assertEquals(taskDTO.getId(), result.getId());
        assertEquals(taskDTO.getDescription(), result.getDescription());
        assertEquals(taskDTO.getDeadline(), result.getDeadline());
        assertEquals(existingStatus, result.getStatus());

        verify(taskRepository).findById(taskId);
        verify(statusRepository).findById(existingStatus.getId());
        verify(statusRepository).save(existingStatus);
        verify(taskRepository).save(existingTask);
        verifyNoMoreInteractions(taskRepository);
        verifyNoMoreInteractions(statusRepository);
    }

    @Test
    void testUpdateTask_TaskDoesNotExist_ReturnsNull() {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setDescription("Updated description");
        taskDTO.setDeadline("2023-12-31");

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Task result = taskService.updateTask(taskId, taskDTO);

        assertNull(result);

        verify(taskRepository).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
        verifyNoInteractions(statusRepository);
    }

    @Test
    void testUpdateTask_StatusDoesNotExist_ReturnsNull() {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setDescription("Updated description");
        taskDTO.setDeadline("2023-12-31");

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setDescription("Original description");
        existingTask.setDeadline("2023-12-31");
        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingTask.setStatus(existingStatus);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(statusRepository.findById(existingStatus.getId())).thenReturn(Optional.empty());

        Task result = taskService.updateTask(taskId, taskDTO);

        assertNull(result);

        verify(taskRepository).findById(taskId);
        verify(statusRepository).findById(existingStatus.getId());
        verifyNoMoreInteractions(taskRepository);
        verifyNoMoreInteractions(statusRepository);
    }

    @Test
    void testPatchTask_TaskExistsAndStatusExists_TaskPatchedAndStatusSaved() {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDescription("Patched description");
        taskDTO.setDeadline("2023-12-31");

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setDescription("Original description");
        existingTask.setDeadline("2023-12-31");
        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingTask.setStatus(existingStatus);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(statusRepository.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));

        Task result = taskService.patchTask(taskId, taskDTO);

        assertEquals(existingTask, result);
        assertEquals(taskDTO.getDescription(), result.getDescription());
        assertEquals(taskDTO.getDeadline(), result.getDeadline());
        assertEquals(existingStatus, result.getStatus());

        verify(taskRepository).findById(taskId);
        verify(statusRepository).findById(existingStatus.getId());
        verify(statusRepository).save(existingStatus);
        verify(taskRepository).save(existingTask);
    }

    @Test
    void testPatchTask_TaskDoesNotExist_ReturnsNull() {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDescription("Patched description");
        taskDTO.setDeadline("2023-12-31");

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Task result = taskService.patchTask(taskId, taskDTO);

        assertNull(result);

        verify(taskRepository).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
        verifyNoInteractions(statusRepository);
    }

    @Test
    void testPatchTask_StatusDoesNotExist_ReturnsNull() {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDescription("Patched description");
        taskDTO.setDeadline("2023-12-31");

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setDescription("Original description");
        existingTask.setDeadline("2023-12-31");
        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingTask.setStatus(existingStatus);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(statusRepository.findById(existingStatus.getId())).thenReturn(Optional.empty());

        Task result = taskService.patchTask(taskId, taskDTO);

        assertNull(result);

        verify(taskRepository).findById(taskId);
        verify(statusRepository).findById(existingStatus.getId());
        verifyNoMoreInteractions(taskRepository);
        verifyNoMoreInteractions(statusRepository);
    }

    @Test
    void testDeleteTaskById_TaskExists_TaskDeletedAndReturnsTrue() {
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        boolean result = taskService.deleteTaskById(taskId);

        assertTrue(result);

        verify(taskRepository).findById(taskId);
        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void testDeleteTaskById_TaskDoesNotExist_ReturnsFalse() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        boolean result = taskService.deleteTaskById(taskId);

        assertFalse(result);

        verify(taskRepository).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }
}
