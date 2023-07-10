import { Component, OnInit } from '@angular/core';
import { Task } from '../Classes/Task';
import { TasksService } from '../Services/tasks.service';
import { GradeService } from '../Services/grade.service';

@Component({
  selector: 'app-student-grades',
  templateUrl: './student-grades.component.html',
  styleUrls: ['./student-grades.component.css']
})
export class StudentGradesComponent implements OnInit {
  tasks: Task[] = [];
  studentId = 260; // Student ID 260
  grades: number[] = [];

  constructor(private tasksService: TasksService, private gradeService: GradeService) {}

  ngOnInit() {
    this.loadTasks();
  }

  loadTasks() {
    this.tasksService.getTasksByActivity(5).subscribe(
      (tasks: Task[]) => {
        this.tasks = tasks;
        this.loadGrades();
      },
      (error: any) => {
        console.error(error);
      }
    );
  }

  loadGrades() {
    this.grades = [];
    this.tasks.forEach((task) => {
      this.gradeService.getStudentMeanGrade(task.id, this.studentId).subscribe(
        (studentGrade: number) => {
          this.grades.push(studentGrade);
        },
        (error: any) => {
          console.error(error);
        }
      );
    });
  }
}
