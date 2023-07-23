package com.project.pc.controller;

import com.project.pc.dto.TeamDTO;
import com.project.pc.exceptions.IncompleteTeamException;
import com.project.pc.exceptions.NotFoundException;
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
    public ResponseEntity<?> createTeam(@RequestBody Team team){
        try {
            TeamDTO newTeam = teamService.createTeam(team);
            studentService.addToTeam(team.getTeamLeader(), newTeam.getId());
            return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
        } catch(IllegalArgumentException | IncompleteTeamException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("{id}/activities/{aId}")
    public ResponseEntity<?> assignActivity(@PathVariable("id") Long id, @PathVariable("aId") Long aId){
        try {
            Team team = teamService.assignActivity(id, aId);
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("{id}/mentors/{mId}")
    public ResponseEntity<?> assignMentor(@PathVariable("id") Long id, @PathVariable("mId") Long mId){
        try {
            Team team = teamService.assignMentor(id, mId);
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams(){
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }
    @GetMapping("leader/{id}")
    public ResponseEntity<?> getTeamByTeamLeader(@PathVariable("id") Long id){
        try {
            TeamDTO team = teamService.getTeamByTeamLeader(id);
            return new ResponseEntity<>(team, HttpStatus.OK);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getTeamById(@PathVariable("id") Long id){
        try {
            TeamDTO team = teamService.getTeamById(id);
            return new ResponseEntity<>(team, HttpStatus.OK);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateTeam(@PathVariable("id") Long id, @RequestBody TeamDTO teamDTO){
        try {
            Team update = teamService.updateTeam(id, teamDTO);
            return new ResponseEntity<>(update, HttpStatus.OK);
        } catch (NotFoundException | IncompleteTeamException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTeamById(@PathVariable("id") Long id){
        if (teamService.deleteTeamById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}