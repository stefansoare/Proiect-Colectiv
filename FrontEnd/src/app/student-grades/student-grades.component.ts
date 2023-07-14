import { Component, OnInit } from '@angular/core';
import { Task } from '../Classes/Task';
import { TasksService } from '../Services/tasks.service';
import { GradeService } from '../Services/grade.service';
import { Observable, forkJoin } from 'rxjs';

@Component({
  selector: 'app-student-grades',
  templateUrl: './student-grades.component.html',
  styleUrls: ['./student-grades.component.css']
})
export class StudentGradesComponent implements OnInit {
  tasks: Task[] = [];
  studentId = 1; // Student ID 
  grades: number[] = [];
  attendances: boolean[] = [];

  constructor(private tasksService: TasksService, private gradeService: GradeService) {}

  ngOnInit() {
    this.loadTasksAndGrades();
  }

  loadTasksAndGrades() {
    this.tasksService.getTasksByActivity(1).subscribe(
      (tasks: Task[]) => {
        this.tasks = tasks;
        this.grades = [];
        this.loadGradesAndAttendancesForTasks();
      },
      (error: any) => {
        console.error(error);
      }
    );
  }
  
  loadGradesAndAttendancesForTasks() {
    const taskObservables: Observable<number>[] = this.tasks.map((task) =>
      this.gradeService.getStudentMeanGrade(task.id, this.studentId)
    );
  
    forkJoin(taskObservables).subscribe(
      (studentGrades: number[]) => {
        this.grades = studentGrades;
        this.loadAttendanceForTasks();
      },
      (error: any) => {
        console.error(error);
      }
    );
  }

  loadAttendanceForTasks() {
    const taskObservables: Observable<boolean>[] = this.tasks.map((task) =>
      this.gradeService.getAttendanceForTask(task.id, this.studentId)
    );
  
    forkJoin(taskObservables).subscribe(
      (attendances: boolean[]) => {
        this.attendances = attendances;
      },
      (error: any) => {
        console.error(error);
      }
    );
  }
}
