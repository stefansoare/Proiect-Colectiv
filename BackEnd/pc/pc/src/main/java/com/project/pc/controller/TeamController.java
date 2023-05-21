package com.project.pc.controller;

import com.project.pc.dto.TeamDTO;
import com.project.pc.model.Team;
import com.project.pc.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/teams/")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team){
        return new ResponseEntity<>(teamService.createTeam(team), HttpStatus.CREATED);
    }
    @PostMapping("{id}/activities/{aId}")
    public ResponseEntity<Team> addToActivity(@PathVariable("id") Long id, @PathVariable("aId") Long aId){
        Team team = teamService.addToActivity(id, aId);
        if (team == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
    @PostMapping("{id}/mentors/{mId}")
    public ResponseEntity<Team> addToMentor(@PathVariable("id") Long id, @PathVariable("mId") Long mId){
        Team team = teamService.addToMentor(id, mId);
        if (team == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams(){
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }
    @GetMapping("leader/{id}")
    public ResponseEntity<TeamDTO> getTeamByTeamLeader(@PathVariable("id") Long id){
        TeamDTO team = teamService.getTeamByTeamLeader(id);
        if (team == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable("id") Long id){
        TeamDTO team = teamService.getTeamById(id);
        if (team == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(team, HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable("id") Long id, @RequestBody Team team){
        Team update = teamService.updateTeam(id, team);
        if (update == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllTeams(){
        return new ResponseEntity<>(teamService.deleteAllTeams());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTeamById(@PathVariable("id") Long id){
        return new ResponseEntity<>(teamService.deleteTeamById(id));
    }
}
