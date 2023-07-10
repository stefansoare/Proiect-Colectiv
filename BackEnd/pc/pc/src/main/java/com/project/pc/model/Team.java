package com.project.pc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private long teamLeader;
    @Column
    private String teamName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Nullable
    private Activity activity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Nullable
    private Mentor mentor;
    @OneToOne
    @JoinColumn
    private Status status;
    public Team() {}
    public Team(long teamLeader) {this.teamLeader = teamLeader;}
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getTeamLeader() {
        return teamLeader;
    }
    public void setTeamLeader(long teamLeader) {
        this.teamLeader = teamLeader;
    }
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public Activity getActivity() {return activity;}
    public void setActivity(Activity activity) {this.activity = activity;}
    public Mentor getMentor() {return mentor;}
    public void setMentor(Mentor mentor) {this.mentor = mentor;}
    public Status getStatus(){return status;}
    public void setStatus(Status status){this.status = status;}
}