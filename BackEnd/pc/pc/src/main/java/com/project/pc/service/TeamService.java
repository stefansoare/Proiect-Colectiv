package com.project.pc.service;

import com.project.pc.exception.CustomException;
import com.project.pc.model.Team;
import com.project.pc.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;
    public Team createTeam(Team team) {
        return teamRepository.save(new Team(team.getTeamLeader()));
    }

    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    public Team getTeamById (Long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        return teamOptional.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                "There is no team with the id : " + id));
    }
}
