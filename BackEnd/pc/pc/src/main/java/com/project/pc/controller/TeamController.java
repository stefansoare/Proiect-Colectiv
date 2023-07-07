package com.project.pc.controller;

import com.project.pc.dto.TeamDTO;
import com.project.pc.model.Task;
import com.project.pc.model.Team;
import com.project.pc.service.StudentService;
import com.project.pc.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/teams/")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private StudentService studentService;
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody TeamDTO teamDTO){
        Team newTeam = teamService.createTeam(teamDTO);
        studentService.addToTeam(teamDTO.getTeamLeader(), newTeam.getId());
        return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
    }
    @PostMapping("{id}/activities/{aId}")
    public ResponseEntity<Team> assignActivity(@PathVariable("id") Long id, @PathVariable("aId") Long aId){
        Team team = teamService.assignActivity(id, aId);
        if (team == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
    @PostMapping("{id}/mentors/{mId}")
    public ResponseEntity<Team> assignMentor(@PathVariable("id") Long id, @PathVariable("mId") Long mId){
        Team team = teamService.assignMentor(id, mId);
        if (team == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
    @PostMapping("{id}/tasks/{tId}")
    public ResponseEntity<Task> assignTask(@PathVariable("id") Long id, @PathVariable("tId") Long tId){
        Task task = teamService.assignTask(id, tId);
        if (task == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
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
    public ResponseEntity<Team> updateTeam(@PathVariable("id") Long id, @RequestBody TeamDTO teamDTO){
        Team update = teamService.updateTeam(id, teamDTO);
        if (update == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllTeams(){
        if (teamService.deleteAllTeams()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTeamById(@PathVariable("id") Long id){
        if (teamService.deleteTeamById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}