package com.project.pc.serviceTest;

import com.project.pc.dto.TeamDTO;
import com.project.pc.model.Status;
import com.project.pc.model.Team;
import com.project.pc.repository.StatusRepository;
import com.project.pc.repository.TeamRepository;
import com.project.pc.service.MappingService;
import com.project.pc.service.TeamService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private TeamService teamService;

    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTeam_ValidTeam_CreatesTeamAndReturnsDTO() {
        // Arrange
        Team team = new Team();
        Status status = new Status();
        TeamDTO teamDTO = new TeamDTO();

        when(statusRepository.save(any(Status.class))).thenReturn(status);
        when(mappingService.convertTeamIntoDTO(any(Team.class))).thenReturn(teamDTO);

        // Act
        TeamDTO result = teamService.createTeam(team);

        // Assert
        assertEquals(teamDTO, result);

        // Verify repository method calls
        verify(statusRepository).save(any(Status.class));
        verify(teamRepository).save(team);
        verify(mappingService).convertTeamIntoDTO(team);
    }

    @Test
    void testCreateTeam_NullTeam_ReturnsNull() {
        // Arrange
        Team team = null;

        // Act
        TeamDTO result = teamService.createTeam(team);

        // Assert
        assertNull(result);

        // Verify repository method calls
        verifyNoMoreInteractions(statusRepository);
        verifyNoMoreInteractions(teamRepository);
        verifyNoMoreInteractions(mappingService);
    }
}
