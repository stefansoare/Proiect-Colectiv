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
        Activity activity = new Activity();
        activity.setName(activityDTO.getName());
        activity.setDescription(activityDTO.getDescription());
        return activity;
    }
    public ActivityDTO convertActivityIntoDTO(Activity activity){
        if (activity == null){
            return null;
        }
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName(activity.getName());
        activityDTO.setDescription(activity.getDescription());
        return activityDTO;
    }
    public Mentor convertDTOIntoMentor(MentorDTO mentorDTO){
        if (mentorDTO == null){
            return null;
        }
        Mentor mentor = new Mentor();
        mentor.setName(mentorDTO.getName());
        mentor.setEmail(mentorDTO.getEmail());
        return mentor;
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
        student.setTeam(studentDTO.getTeamDTO());
        student.setTask(studentDTO.getTaskDTO());
        return student;
    }
    public StudentDTO convertStudentIntoDTO(Student student){
        if (student == null){
            return null;
        }
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setTeamDTO(student.getTeam());
        studentDTO.setTaskDTO(student.getTask());
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
        teamDTO.setTeamLeader(team.getTeamLeader());
        return teamDTO;
    }
    public Task convertDTOIntoTask(TaskDTO taskDTO){
        if (taskDTO == null){
            return null;
        }
        Task task = new Task();
        task.setGrade(taskDTO.getGrade());
        task.setDescription(taskDTO.getDescription());
        task.setDeadline(taskDTO.getDeadline());
        task.setAttendance(taskDTO.getAttendance());
        task.setComment(taskDTO.getComment());
        task.setActivity(taskDTO.getActivityDTO());
        return task;
    }
    public TaskDTO convertTaskIntoDTO(Task task){
        if (task == null){
            return null;
        }
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setGrade(task.getGrade());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDeadline(task.getDeadline());
        taskDTO.setAttendance(task.getAttendance());
        taskDTO.setComment(task.getComment());
        taskDTO.setActivityDTO(task.getActivity());
        return taskDTO;
    }
}
