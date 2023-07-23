package com.project.pc.model;

import jakarta.persistence.*;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String name;
    @Column
    private String description;
    @OneToOne
    @JoinColumn
    private Status status;
    public Activity() {}
    public Activity(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Activity(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Activity(long id) {
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Status getStatus(){return status;}
    public void setStatus(Status status){this.status = status;}
}