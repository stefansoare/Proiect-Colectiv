import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Task } from '../Classes/Task';
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';

@Component({
  selector: 'app-grading-dialog',
  templateUrl: './grading-dialog.component.html',
  styleUrls: ['./grading-dialog.component.css']
})
export class GradingDialogComponent implements OnInit {
  task: Task;
  students: Student[] = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Task,
    private studentService: StudentService
  ) {
    this.task = data;
  }

  displayedColumns: string[] = ['name', 'grade', 'attendance', 'comment'];

  ngOnInit(): void {
    this.loadStudents(this.task.activity_id);
  }

  loadStudents(activityId: number): void {
    this.studentService.getActivityStudents(activityId).subscribe(
      students => {
        this.students = students;
      },
      error => {
        console.error('Error loading students:', error);
      }
    );
  }
}
