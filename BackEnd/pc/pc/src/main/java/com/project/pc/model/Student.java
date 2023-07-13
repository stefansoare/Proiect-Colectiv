package com.project.pc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.pc.dto.GradeDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String name;
    @Column
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Nullable
    private Team team;
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "student")
    private List<Grade> grades;
    @OneToOne
    @JoinColumn
    private Status status;
    public Student() {}
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Student(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Team getTeam() {return team;}
    public void setTeam(Team team) {this.team = team;}
    public List<Grade> getGrades() {
        return grades;
    }
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
    public void addToGrades(Grade grade){
        this.grades.add(grade);
    }
    public Status getStatus(){return status;}
    public void setStatus(Status status){this.status = status;}
}