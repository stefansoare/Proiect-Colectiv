package com.project.pc.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String teamLeader;
    public Team() {}

    public Team(String teamLeader) {
        this.teamLeader = teamLeader;
    }
    public Team(long id, String teamLeader) {
        this.id = id;
        this.teamLeader = teamLeader;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTeamLeader() {
        return teamLeader;
    }
    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }
}
