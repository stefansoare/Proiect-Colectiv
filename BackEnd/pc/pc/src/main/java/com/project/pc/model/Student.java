package com.project.pc.model;

import jakarta.persistence.*;

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
    @Column
    private int leader;
    public Student() {}
    public Student(long id, String name, String email, int leader) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.leader = leader;
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
    public int getLeader() {
        return leader;
    }
    public void setLeader(int leader) {
        this.leader = leader;
    }
}
