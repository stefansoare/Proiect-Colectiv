package com.project.pc.controller;

import com.project.pc.model.Team;
import com.project.pc.repository.TeamRepository;
import com.project.pc.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamRepository teamRepository;
    private Long id;

    @PostMapping("/createTeam")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        if(teams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping("/team/{teamId}")
    public Team getTeam(@PathVariable("teamId") Long id) {
        return teamService.getTeamById(id);
    }

    @PutMapping("/team/{teamId}")
    public ResponseEntity<Team> updateTeam(@PathVariable("teamId") Long teamId,
                                           @RequestBody Team newTeam) {
        Team team = teamService.updateTeam(teamId, newTeam);
        if (team == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teamRepository.save(team), HttpStatus.OK);
    }

    @DeleteMapping("/teams")
    public ResponseEntity<HttpStatus> deleteAllTeams() {
        return new ResponseEntity<>(teamService.deleteAllTeams());
    }

    @DeleteMapping("/team/{teamId}")
    public ResponseEntity<HttpStatus> deleteTeamById(@PathVariable("teamId") Long teamId) {
        return new ResponseEntity<>(teamService.deleteTeamById(teamId));
    }
}
