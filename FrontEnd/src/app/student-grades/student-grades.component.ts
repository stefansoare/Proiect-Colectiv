import { Component, OnInit } from '@angular/core';
import { Task } from '../Classes/Task';
import { TasksService } from '../Services/tasks.service';

@Component({
  selector: 'app-student-grades',
  templateUrl: './student-grades.component.html',
  styleUrls: ['./student-grades.component.css']
})
export class StudentGradesComponent implements OnInit {
  tasks: Task[] = [];

  constructor(private tasksService: TasksService) {}

  ngOnInit() {
    this.loadTasks();
  }

  loadTasks() {
    const studentId = 1; // Replace with the actual student ID
    this.tasksService.getAllTasksOfAStudent(studentId).subscribe(
      tasks => {
        this.tasks = tasks;
      },
      error => {
        console.error(error);
      }
    );
  }
}
