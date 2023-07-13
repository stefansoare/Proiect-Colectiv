package com.project.pc.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String description;
    @Column
    private String deadline;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Nullable
    private Activity activity;
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "task")
    private List<Grade> grades;
    @OneToOne
    @JoinColumn
    private Status status;
    public Task(){
        this.description = "";
        this.deadline = "";
    }
    public Task(String description, String deadline) {
        this.description = description;
        this.deadline = deadline;
    }

    public Task(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDeadline() {
        return deadline;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public Activity getActivity(){return activity;}
    public void setActivity(Activity activity){this.activity = activity;}
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