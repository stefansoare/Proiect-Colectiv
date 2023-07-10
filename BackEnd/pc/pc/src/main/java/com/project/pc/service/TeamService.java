package com.project.pc.service;

import com.project.pc.dto.TeamDTO;
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
    public TeamDTO createTeam(Team team){
        if (team == null)
            return null;
        Status status = new Status();
        statusRepository.save(status);
        team.setStatus(status);
        teamRepository.save(team);
        return mappingService.convertTeamIntoDTO(team);
    }
    public Team assignActivity(Long id, Long aId){
        Team team = teamRepository.findById(id).orElse(null);
        Activity activity = activityRepository.findById(aId).orElse(null);
        if (team == null || activity == null){
            return null;
        }
        Status status = statusRepository.findById(team.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        team.setActivity(activity);
        team.setStatus(status);
        teamRepository.save(team);
        return team;
    }
    public Team assignMentor(Long id, Long mId){
        Team team = teamRepository.findById(id).orElse(null);
        Mentor mentor = mentorRepository.findById(mId).orElse(null);
        if (team == null || mentor == null){
            return null;
        }
        Status status = statusRepository.findById(team.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
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
    public TeamDTO getTeamById(Long id){
        return mappingService.convertTeamIntoDTO(teamRepository.findById(id).orElse(null));
    }
    public TeamDTO getTeamByTeamLeader(Long id){
        return mappingService.convertTeamIntoDTO(teamRepository.findByTeamLeader(id).orElse(null));
    }
    public Team updateTeam(Long id, TeamDTO teamDTO){
        Team update = teamRepository.findById(id).orElse(null);
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