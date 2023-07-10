package com.project.pc.serviceTest;

import com.project.pc.dto.ActivityDTO;
import com.project.pc.model.Activity;
import com.project.pc.repository.ActivityRepository;
import com.project.pc.service.ActivityService;
import com.project.pc.service.MappingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ActivityServiceTest {
    @Mock
    private ActivityDTO activityDTOMock;

    @Mock
    private ActivityRepository activityRepositoryMock;

    @Mock
    private MappingService mappingServiceMock;

    @InjectMocks
    private ActivityService activityService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateActivity() {
        ActivityDTO activityDTO = new ActivityDTO();
        Activity activity = new Activity();
        when(activityRepositoryMock.save(any(Activity.class))).thenReturn(activity);
        when(mappingServiceMock.convertDTOIntoActivity(activityDTO)).thenReturn(activity);

        Activity result = activityService.createActivity(activityDTO);

        verify(activityRepositoryMock, times(1)).save(any(Activity.class));
        verify(mappingServiceMock, times(1)).convertDTOIntoActivity(activityDTO);
        Assert.assertEquals(activity, result);
    }

    @Test
    public void testCreateActivityWithNullDTO() {
        ActivityDTO activityDTO = null;

        Activity result = activityService.createActivity(activityDTO);

        Assert.assertNull(result);
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


        verify(activityRepositoryMock, times(1)).findAll();
        verify(mappingServiceMock, times(2)).convertActivityIntoDTO(any(Activity.class));
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


        verify(activityRepositoryMock, times(1)).findById(activityId);
        verify(mappingServiceMock, times(1)).convertActivityIntoDTO(activity);
        Assert.assertEquals(activityDTO, result);
    }

    @Test
    public void testGetActivityByIdWithNull() {
        Long activityId = 1L;
        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.empty());


        ActivityDTO result = activityService.getActivityById(activityId);


        verify(activityRepositoryMock, times(1)).findById(activityId);
        verify(mappingServiceMock, never()).convertActivityIntoDTO(any(Activity.class));
        Assert.assertNull(result);
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


        verify(activityRepositoryMock, times(1)).findByName(activityName);
        verify(mappingServiceMock, times(1)).convertActivityIntoDTO(activity);
        Assert.assertEquals(activityDTO, result);
    }

    @Test
    public void testGetActivityByNameWithNull() {
        String activityName = "Non-existent Activity";
        when(activityRepositoryMock.findByName(activityName)).thenReturn(Optional.empty());


        ActivityDTO result = activityService.getActivityByName(activityName);


        verify(activityRepositoryMock, times(1)).findByName(activityName);
        verify(mappingServiceMock, never()).convertActivityIntoDTO(any(Activity.class));
        Assert.assertNull(result);
    }

    @Test
    public void testUpdateActivity() {
        // Arrange
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

        // Act
        Activity result = activityService.updateActivity(activityId, activityDTO);

        // Assert
        verify(activityRepositoryMock, times(1)).findById(activityId);
        verify(mappingServiceMock, times(1)).convertDTOIntoActivity(activityDTO);
        verify(activityRepositoryMock, times(1)).save(existingActivity);

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

        when(activityRepositoryMock.findById(activityId)).thenReturn(Optional.of(existingActivity));


        Activity patchedActivity = activityService.patchActivity(activityId, activityDTO);


        verify(activityRepositoryMock, times(1)).findById(activityId);
        verify(activityRepositoryMock, times(1)).save(existingActivity);

        Assert.assertEquals(updatedName, patchedActivity.getName());
        Assert.assertEquals(updatedDescription, patchedActivity.getDescription());
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
        Assert.assertNull(result);
    }

    @Test
    public void testDeleteAllActivities() {
        boolean result = activityService.deleteAllActivities();

        verify(activityRepositoryMock, times(1)).deleteAll();
        Assert.assertTrue(result);
    }

    @Test
    public void testDeleteActivityByName() {
        String activityName = "Test Activity";
        Long activityId = 1L;
        Optional<Activity> existingActivity = Optional.of(new Activity(activityId, activityName));

        when(activityRepositoryMock.findByName(activityName)).thenReturn(existingActivity);


        boolean result = activityService.deleteActivityByName(activityName);


        verify(activityRepositoryMock, times(1)).findByName(activityName);
        verify(activityRepositoryMock, times(1)).deleteById(activityId);
        Assert.assertTrue(result);
    }

    @Test
    public void testDeleteActivityByNameWithNonExistentName() {
        String nonExistentName = "Non-existent Activity";

        when(activityRepositoryMock.findByName(nonExistentName)).thenReturn(Optional.empty());


        boolean result = activityService.deleteActivityByName(nonExistentName);


        verify(activityRepositoryMock, times(1)).findByName(nonExistentName);
        verify(activityRepositoryMock, never()).deleteById(anyLong());
        Assert.assertFalse(result);
    }

    @Test
    public void testDeleteActivityById() {
        Long activityId = 1L;
        Optional<Activity> existingActivity = Optional.of(new Activity(activityId, "Test Activity"));

        when(activityRepositoryMock.findById(activityId)).thenReturn(existingActivity);


        boolean result = activityService.deleteActivityById(activityId);


        verify(activityRepositoryMock, times(1)).findById(activityId);
        verify(activityRepositoryMock, times(1)).deleteById(activityId);
        Assert.assertTrue(result);
    }

    @Test
    public void testDeleteActivityByIdWithNonExistentId() {
        Long nonExistentId = 999L;

        when(activityRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());


        boolean result = activityService.deleteActivityById(nonExistentId);


        verify(activityRepositoryMock, times(1)).findById(nonExistentId);
        verify(activityRepositoryMock, never()).deleteById(anyLong());
        Assert.assertFalse(result);
    }
}

