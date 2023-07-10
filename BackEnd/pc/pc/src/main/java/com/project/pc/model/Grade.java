package com.project.pc.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "grades")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private long grade;
    @Column
    private boolean attendance;
    @Column
    private String comment;
    @Column
    private String date;
    // give grade from mentor to student for task, fill mentor id, student id, task id
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    public Grade() {
    }
    public Grade(long grade, boolean attendance, String comment) {
        this.grade = grade;
        this.attendance = attendance;
        this.comment = comment;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getGrade() {
        return grade;
    }
    public void setGrade(long grade) {
        this.grade = grade;
    }
    public boolean isAttendance() {
        return attendance;
    }
    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getDate() {
        return date;
    }
    public void setDate() {
        Date date1 = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy-hh:mm");
        this.date = dateFormat.format(date1);
    }
    public Mentor getMentor() {
        return mentor;
    }
    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }
}
