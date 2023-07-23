package com.project.pc.service;

import com.project.pc.dto.TeamDTO;
import com.project.pc.exceptions.IncompleteTeamException;
import com.project.pc.exceptions.NotFoundException;
import com.project.pc.model.*;
import com.project.pc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private MappingService mappingService;
    public TeamDTO createTeam(Team team) throws IllegalArgumentException, IncompleteTeamException {
        if (team == null){
            throw new IllegalArgumentException("Team cannot be null");
        }
        Long teamLeaderLong = Long.valueOf(team.getTeamLeader());
        if (teamLeaderLong == null || team.getTeamName() == null){
            throw new IncompleteTeamException("Team is missing some required fields");
        }
        Status status = new Status();
        statusRepository.save(status);
        team.setStatus(status);
        teamRepository.save(team);
        return mappingService.convertTeamIntoDTO(team);
    }
    public Team assignActivity(Long id, Long aId) throws NotFoundException {
        Team team = teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Team not found with ID: " + id));
        Activity activity = activityRepository.findById(aId).orElseThrow(() -> new NotFoundException("Activity not found with ID: " + id));
        Status status = statusRepository.findById(team.getStatus().getId()).orElseThrow(() -> new NotFoundException("Status not found with ID: " + id));
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        team.setActivity(activity);
        team.setStatus(status);
        teamRepository.save(team);
        return team;
    }
    public Team assignMentor(Long id, Long mId) throws NotFoundException{
        Team team = teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Team not found with ID: " + id));
        Mentor mentor = mentorRepository.findById(mId).orElseThrow(() -> new NotFoundException("Mentor not found with ID: " + id));
        Status status = statusRepository.findById(team.getStatus().getId()).orElseThrow(() -> new NotFoundException("Status not found with ID: " + id));
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        team.setMentor(mentor);
        team.setStatus(status);
        teamRepository.save(team);
        return team;
    }
    public List<TeamDTO> getAllTeams(){
        List<Team> teams = teamRepository.findAll();
        List<TeamDTO> teamDTOS = new ArrayList<>();
        for (Team team : teams){
            teamDTOS.add(mappingService.convertTeamIntoDTO(team));
        }
        return teamDTOS;
    }
    public TeamDTO getTeamById(Long id) throws NotFoundException{
        return mappingService.convertTeamIntoDTO(teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Team not found with ID: " + id)));
    }
    public TeamDTO getTeamByTeamLeader(Long id) throws NotFoundException{
        return mappingService.convertTeamIntoDTO(teamRepository.findByTeamLeader(id).orElseThrow(() -> new NotFoundException("Team not found with team leader: " + id)));
    }
    public Team updateTeam(Long id, TeamDTO teamDTO) throws NotFoundException, IncompleteTeamException{
        Team update = teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Team not found with ID: " + id));
        Status status = statusRepository.findById(update.getStatus().getId()).orElseThrow(() -> new NotFoundException("Status not found with ID: " + id));
        Long teamLeaderLong = Long.valueOf(teamDTO.getTeamLeader());
        if (teamLeaderLong == null || teamDTO.getTeamName() == null){
            throw new IncompleteTeamException("Team is missing some required fields");
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        update.setTeamLeader(teamDTO.getTeamLeader());
        update.setTeamName(teamDTO.getTeamName());
        update.setStatus(status);
        teamRepository.save(update);
        return update;
    }
    public boolean deleteTeamById(Long id){
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()){
            teamRepository.deleteById(id);
            return true;
        }
        return false;
    }
}