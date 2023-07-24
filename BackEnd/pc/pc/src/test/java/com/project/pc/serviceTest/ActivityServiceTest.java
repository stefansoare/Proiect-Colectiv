package com.project.pc.serviceTest;

import com.project.pc.dto.ActivityDTO;
import com.project.pc.model.Activity;
import com.project.pc.model.Status;
import com.project.pc.repository.ActivityRepository;
import com.project.pc.repository.StatusRepository;
import com.project.pc.service.ActivityService;
import com.project.pc.service.MappingService;
import com.project.pc.exceptions.IncompleteActivityException;
import com.project.pc.exceptions.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Rollback
public class ActivityServiceTest {
    @Mock
    private ActivityRepository activityRepositoryMock;

    @Mock
    private MappingService mappingServiceMock;

    @Mock
    private StatusRepository statusRepositoryMock;

    @InjectMocks
    private ActivityService activityService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCreateActivityValidActivity() throws IncompleteActivityException, IllegalArgumentException {
        Activity activity = new Activity();
        activity.setName("Test Activity");
        activity.setDescription("Test Description");

        Status savedStatus = new Status();
        when(statusRepositoryMock.save(any(Status.class))).thenReturn(savedStatus);

        Activity savedActivity = new Activity();
        when(activityRepositoryMock.save(any(Activity.class))).thenReturn(savedActivity);

        ActivityDTO expectedDto = new ActivityDTO();
        when(mappingServiceMock.convertActivityIntoDTO(any(Activity.class))).thenReturn(expectedDto);

        ActivityDTO resultDto = activityService.createActivity(activity);

        assertEquals(expectedDto, resultDto);

        verify(statusRepositoryMock, times(1)).save(any(Status.class));
        verify(activityRepositoryMock, times(1)).save(any(Activity.class));

        verify(mappingServiceMock, times(1)).convertActivityIntoDTO(any(Activity.class));
    }

    @Test(expected = IncompleteActivityException.class)
    public void testCreateActivityIncompleteActivity() throws IncompleteActivityException, IllegalArgumentException {
        Activity incompleteActivity = new Activity();
        incompleteActivity.setName("Test Activity");

        activityService.createActivity(incompleteActivity);
    }

    @Test
    public void testGetAllActivities() {
        Activity activity1 = new Activity();
        activity1.setId(1L);
        activity1.setName("Activity 1");

        Activity activity2 = new Activity();
        activity2.setId(2L);
        activity2.setName("Activity 2");

        List<Activity> activities = new ArrayList<>();
        activities.add(activity1);
        activities.add(activity2);

        when(activityRepositoryMock.findAll()).thenReturn(activities);

        ActivityDTO activityDTO1 = new ActivityDTO();
        activityDTO1.setId(1L);
        activityDTO1.setName("Activity 1 DTO");

        ActivityDTO activityDTO2 = new ActivityDTO();
        activityDTO2.setId(2L);
        activityDTO2.setName("Activity 2 DTO");

        when(mappingServiceMock.convertActivityIntoDTO(activity1)).thenReturn(activityDTO1);
        when(mappingServiceMock.convertActivityIntoDTO(activity2)).thenReturn(activityDTO2);

        List<ActivityDTO> result = activityService.getAllActivities();

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(activityDTO1, result.get(0));
        Assert.assertEquals(activityDTO2, result.get(1));
    }
    @Test
    public void testGetActivityByIdActivityFound() throws NotFoundException {
        Long activityId = 1L;
        Activity activity = new Activity();
        activity.setId(activityId);

        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.of(activity));

        ActivityDTO expectedDto = new ActivityDTO();
        when(mappingServiceMock.convertActivityIntoDTO(activity)).thenReturn(expectedDto);

        ActivityDTO resultDto = activityService.getActivityById(activityId);

        assertEquals(expectedDto, resultDto);

        verify(activityRepositoryMock, times(1)).findById(activityId);

        verify(mappingServiceMock, times(1)).convertActivityIntoDTO(activity);
    }

    @Test(expected = NotFoundException.class)
    public void testGetActivityByIdActivityNotFound() throws NotFoundException {
        Long activityId = 2L;

        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.empty());

        activityService.getActivityById(activityId);
    }
    @Test
    public void testGetActivityByNameActivityFound() throws NotFoundException {
        String activityName = "Test Activity";
        Activity activity = new Activity();
        activity.setName(activityName);

        when(activityRepositoryMock.findByName(activityName)).thenReturn(Optional.of(activity));

        ActivityDTO expectedDto = new ActivityDTO();
        when(mappingServiceMock.convertActivityIntoDTO(activity)).thenReturn(expectedDto);

        ActivityDTO resultDto = activityService.getActivityByName(activityName);

        assertEquals(expectedDto, resultDto);

        verify(activityRepositoryMock, times(1)).findByName(activityName);

        verify(mappingServiceMock, times(1)).convertActivityIntoDTO(activity);
    }

    @Test(expected = NotFoundException.class)
    public void testGetActivityByNameActivityNotFound() throws NotFoundException {
        String activityName = "Non-existent Activity";

        when(activityRepositoryMock.findByName(activityName)).thenReturn(Optional.empty());

        activityService.getActivityByName(activityName);
    }

    @Test
    public void testUpdateActivityActivityFound() throws NotFoundException, IncompleteActivityException {
        Long activityId = 1L;
        String newName = "Updated Activity";
        String newDescription = "Updated Description";

        Activity existingActivity = new Activity();
        existingActivity.setId(activityId);
        existingActivity.setName("Old Activity");
        existingActivity.setDescription("Old Description");

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName(newName);
        activityDTO.setDescription(newDescription);

        Status existingStatus = new Status();
        existingStatus.setId(1L);

        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.of(existingActivity));

        when(statusRepositoryMock.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));

        when(activityRepositoryMock.save(any(Activity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Activity updatedActivity = activityService.updateActivity(activityId, activityDTO);

        assertEquals(activityId, updatedActivity.getId());
        assertEquals(newName, updatedActivity.getName());
        assertEquals(newDescription, updatedActivity.getDescription());

        verify(activityRepositoryMock, times(1)).findById(activityId);

        verify(statusRepositoryMock, times(1)).findById(existingStatus.getId());

        verify(existingStatus, times(1)).setModifiedBy();
        verify(existingStatus, times(1)).setModificationDate();

        verify(statusRepositoryMock, times(1)).save(existingStatus);

        verify(activityRepositoryMock, times(1)).save(existingActivity);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateActivityActivityNotFound() throws NotFoundException, IncompleteActivityException {
        Long activityId = 2L;

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName("New Activity");
        activityDTO.setDescription("New Description");

        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.empty());

        activityService.updateActivity(activityId, activityDTO);
    }

    @Test(expected = IncompleteActivityException.class)
    public void testUpdateActivityIncompleteActivity() throws NotFoundException, IncompleteActivityException {
        Long activityId = 3L;

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName(null);
        activityDTO.setDescription("Incomplete Description");

        Activity existingActivity = new Activity();
        existingActivity.setId(activityId);

        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.of(existingActivity));
        when(statusRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Status()));

        activityService.updateActivity(activityId, activityDTO);
    }
    @Test
    public void testPatchActivityActivityFound() throws NotFoundException {
        Long activityId = 1L;
        String newName = "Updated Activity";
        String newDescription = "Updated Description";

        Activity existingActivity = new Activity();
        existingActivity.setId(activityId);
        existingActivity.setName("Old Activity");
        existingActivity.setDescription("Old Description");

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName(newName);
        activityDTO.setDescription(newDescription);

        Status existingStatus = new Status();
        existingStatus.setId(1L);

        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.of(existingActivity));

        when(statusRepositoryMock.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));

        when(activityRepositoryMock.save(any(Activity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Activity patchedActivity = activityService.patchActivity(activityId, activityDTO);

        assertEquals(activityId, patchedActivity.getId());
        assertEquals(newName, patchedActivity.getName());
        assertEquals(newDescription, patchedActivity.getDescription());

        verify(activityRepositoryMock, times(1)).findById(activityId);

        verify(statusRepositoryMock, times(1)).findById(existingStatus.getId());

        verify(existingStatus, times(1)).setModifiedBy();
        verify(existingStatus, times(1)).setModificationDate();

        verify(statusRepositoryMock, times(1)).save(existingStatus);

        verify(activityRepositoryMock, times(1)).save(existingActivity);
    }

    @Test(expected = NotFoundException.class)
    public void testPatchActivityActivityNotFound() throws NotFoundException {
        Long activityId = 2L;

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName("New Activity");
        activityDTO.setDescription("New Description");

        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.empty());

        activityService.patchActivity(activityId, activityDTO);
    }

    @Test
    public void testDeleteActivityByName() {
        String activityName = "Test Activity";
        Long activityId = 1L;
        Optional<Activity> existingActivity = Optional.of(new Activity(activityId, activityName));

        when(activityRepositoryMock.findByName(activityName)).thenReturn(existingActivity);

        boolean result = activityService.deleteActivityByName(activityName);

        Assert.assertTrue(result);
    }

    @Test
    public void testDeleteActivityById() {
        Long activityId = 1L;
        Optional<Activity> existingActivity = Optional.of(new Activity(activityId, "Test Activity"));

        when(activityRepositoryMock.findById(activityId)).thenReturn(existingActivity);

        boolean result = activityService.deleteActivityById(activityId);

        Assert.assertTrue(result);
    }
}

