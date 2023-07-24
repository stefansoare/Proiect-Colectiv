package com.project.pc.serviceTest;

import com.project.pc.dto.ActivityDTO;
import com.project.pc.exception.NullObjectException;
import com.project.pc.model.Activity;
import com.project.pc.model.Status;
import com.project.pc.repository.ActivityRepository;
import com.project.pc.repository.StatusRepository;
import com.project.pc.service.ActivityService;
import com.project.pc.service.MappingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
    public void testCreateActivity() throws NullObjectException {
        Activity activity = new Activity();
        ActivityDTO activityDTO = new ActivityDTO();

        when(activityRepositoryMock.save(activity)).thenReturn(activity);
        when(statusRepositoryMock.save(any(Status.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mappingServiceMock.convertActivityIntoDTO(activity)).thenReturn(activityDTO);

        ActivityDTO result = activityService.createActivity(activity);

        Assert.assertEquals(activityDTO, result);
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
    public void testGetActivityById() {
        Long activityId = 1L;
        Activity activity = new Activity();
        activity.setId(activityId);
        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.of(activity));

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setId(activityId);
        when(mappingServiceMock.convertActivityIntoDTO(activity)).thenReturn(activityDTO);

        ActivityDTO result = activityService.getActivityById(activityId);

        Assert.assertEquals(activityDTO, result);
    }

    @Test
    public void testGetActivityByName() {
        String activityName = "Test Activity";
        Activity activity = new Activity();
        activity.setName(activityName);
        when(activityRepositoryMock.findByName(activityName)).thenReturn(Optional.of(activity));

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName(activityName);
        when(mappingServiceMock.convertActivityIntoDTO(activity)).thenReturn(activityDTO);

        ActivityDTO result = activityService.getActivityByName(activityName);

        Assert.assertEquals(activityDTO, result);
    }

    @Test
    public void testUpdateActivity() {
        Long activityId = 1L;
        String updatedName = "Updated Activity";
        String updatedDescription = "Updated Description";

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName(updatedName);
        activityDTO.setDescription(updatedDescription);

        Activity existingActivity = new Activity();
        existingActivity.setId(activityId);
        existingActivity.setName("Existing Activity");
        existingActivity.setDescription("Existing Description");

        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.of(existingActivity));
        when(mappingServiceMock.convertDTOIntoActivity(activityDTO)).thenReturn(existingActivity);

        Activity result = activityService.updateActivity(activityId, activityDTO);

        Assert.assertEquals(existingActivity, result);
        Assert.assertEquals(updatedName, result.getName());
        Assert.assertEquals(updatedDescription, result.getDescription());
    }


    @Test
    public void testUpdateActivityWithNonExistentId() {
        Long nonExistentId = 999L;
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName("Updated Activity");
        activityDTO.setDescription("Updated Description");

        when(activityRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());


        Activity result = activityService.updateActivity(nonExistentId, activityDTO);


        verify(activityRepositoryMock, times(1)).findById(nonExistentId);
        verify(mappingServiceMock, never()).convertActivityIntoDTO(any(Activity.class));
        verify(activityRepositoryMock, never()).save(any(Activity.class));
        verifyNoMoreInteractions(activityRepositoryMock);
        verifyNoMoreInteractions(mappingServiceMock);
        Assert.assertNull(result);
    }

    @Test
    public void testPatchActivity() {
        Long activityId = 1L;
        String updatedName = "Updated Activity";
        String updatedDescription = "Updated Description";

        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName(updatedName);
        activityDTO.setDescription(updatedDescription);

        Activity existingActivity = new Activity();
        existingActivity.setId(activityId);
        existingActivity.setName("Existing Activity");
        existingActivity.setDescription("Existing Description");

        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingActivity.setStatus(existingStatus);

        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.of(existingActivity));
        when(statusRepositoryMock.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));

        Activity result = activityService.patchActivity(activityId, activityDTO);

        Assert.assertEquals(existingActivity, result);
        Assert.assertEquals(updatedName, result.getName());
        Assert.assertEquals(updatedDescription, result.getDescription());
        Assert.assertEquals(existingStatus, result.getStatus());
    }

    @Test
    public void testPatchActivityWithNonExistentId() {
        Long nonExistentId = 999L;
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName("Updated Activity");
        activityDTO.setDescription("Updated Description");

        when(activityRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());


        Activity result = activityService.patchActivity(nonExistentId, activityDTO);


        verify(activityRepositoryMock, times(1)).findById(nonExistentId);
        verify(activityRepositoryMock, never()).save(any(Activity.class));
        verifyNoMoreInteractions(activityRepositoryMock);
        Assert.assertNull(result);
    }

    public void testPatchActivityWithNonExistentStatus() {
        Long activityId = 1L;
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName("Updated Activity");
        activityDTO.setDescription("Updated Description");

        Activity existingActivity = new Activity();
        existingActivity.setId(activityId);
        existingActivity.setName("Existing Activity");
        existingActivity.setDescription("Existing Description");

        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.of(existingActivity));
        when(statusRepositoryMock.findById(existingActivity.getStatus().getId())).thenReturn(Optional.empty());


        Activity result = activityService.patchActivity(activityId, activityDTO);


        verify(activityRepositoryMock, times(1)).findById(activityId);
        verify(statusRepositoryMock, times(1)).findById(existingActivity.getStatus().getId());
        verify(statusRepositoryMock, never()).save(any(Status.class));
        verify(activityRepositoryMock, never()).save(any(Activity.class));
        verifyNoMoreInteractions(activityRepositoryMock);
        verifyNoMoreInteractions(statusRepositoryMock);

        Assert.assertNull(result);
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

