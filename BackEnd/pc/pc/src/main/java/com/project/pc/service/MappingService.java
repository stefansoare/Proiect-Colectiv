package com.project.pc.service;

import com.project.pc.dto.*;
import com.project.pc.model.*;
import org.springframework.stereotype.Service;

@Service
public class MappingService {
    public Activity convertDTOIntoActivity(ActivityDTO activityDTO){
        if (activityDTO == null){
            return null;
        }
        return new Activity(activityDTO.getName(), activityDTO.getDescription());
    }
    public ActivityDTO convertActivityIntoDTO(Activity activity){
        if (activity == null){
            return null;
        }
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setId(activity.getId());
        activityDTO.setName(activity.getName());
        activityDTO.setDescription(activity.getDescription());
        return activityDTO;
    }
    public Mentor convertDTOIntoMentor(MentorDTO mentorDTO){
        if (mentorDTO == null){
            return null;
        }
        return new Mentor(mentorDTO.getName(), mentorDTO.getEmail());
    }
    public MentorDTO convertMentorIntoDTO(Mentor mentor){
        if (mentor == null){
            return null;
        }
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setName(mentor.getName());
        mentorDTO.setEmail(mentor.getEmail());
        return mentorDTO;
    }
    public Student convertDTOIntoStudent(StudentDTO studentDTO){
        if (studentDTO == null){
            return null;
        }
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        return student;
    }
    public StudentDTO convertStudentIntoDTO(Student student){
        if (student == null){
            return null;
        }
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        if (student.getTeam() != null){
            studentDTO.setTeam_id(student.getTeam().getId());
        }
        return studentDTO;
    }
    public Team convertDTOIntoTeam(TeamDTO teamDTO){
        if (teamDTO == null){
            return null;
        }
        Team team = new Team();
        team.setTeamLeader(teamDTO.getTeamLeader());
        return team;
    }
    public TeamDTO convertTeamIntoDTO(Team team){
        if (team == null){
            return null;
        }
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setTeamLeader(team.getTeamLeader());
        return teamDTO;
    }
    public Task convertDTOIntoTask(TaskDTO taskDTO){
        if (taskDTO == null){
            return null;
        }
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setGrade(taskDTO.getGrade());
        task.setDescription(taskDTO.getDescription());
        task.setDeadline(taskDTO.getDeadline());
        task.setAttendance(taskDTO.getAttendance());
        task.setComment(taskDTO.getComment());

        return task;
    }
    public TaskDTO convertTaskIntoDTO(Task task){
        if (task == null){
            return null;
        }
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setStudent_id(task.getStudent().getId());
        taskDTO.setId(task.getId());
        taskDTO.setGrade(task.getGrade());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDeadline(task.getDeadline());
        taskDTO.setAttendance(task.getAttendance());
        taskDTO.setComment(task.getComment());

        return taskDTO;
    }
}