package com.project.pc.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mentors")
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String name;
    @Column
    private String email;
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "mentor")
    private List<Grade> grades;
    @OneToOne
    @JoinColumn
    private Status status;
    public Mentor(){
        this.grades = new ArrayList<>();
    }
    public Mentor(String name, String email) {
        this.name = name;
        this.email = email;
        this.grades = new ArrayList<>();
    }

    public Mentor(long id) {
        this.id = id;
        this.grades = new ArrayList<>();
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