import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Task } from '../Classes/Task';
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';
import { GradeService } from '../Services/grade.service';
import { Grade } from '../Classes/Grade';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-grading-dialog',
  templateUrl: './grading-dialog.component.html',
  styleUrls: ['./grading-dialog.component.css']
})
export class GradingDialogComponent implements OnInit {
  task: Task;
  students: Student[] = [];
  grade: Grade | undefined;
  

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Task,
    private dialogRef: MatDialogRef<GradingDialogComponent>,
    private studentService: StudentService,
    private gradeService: GradeService
  ) {
    this.task = data;
  }

  displayedColumns: string[] = ['name', 'grade', 'attendance', 'comment'];

  ngOnInit(): void {
    if (this.task && this.task.activity_id) {
      this.loadStudents(this.task.activity_id);
    }
  }

  loadStudents(activityId: number): void {
    this.studentService.getActivityStudents(activityId).subscribe(
      (students: Student[]) => {
        this.students = students;
      },
      (error: any) => {
        console.error('Error loading students:', error);
      }
    );
  }
  saveGrades(): void {
    const gradesToSave: Grade[] = [];
    for (const element of this.students) {
      const grade: Grade = {
        grade: 1,
        attendance: true,
        comment: 'xsa'
      };
      gradesToSave.push(grade);
    }
  
    if (gradesToSave.length > 0) {
      this.gradeService.giveGrades(1, this.students[0].id, this.task.activity_id, gradesToSave).subscribe(
        (response: HttpResponse<Grade[]>) => {
          console.log('Grades saved successfully:', response);
          this.dialogRef.close(true); 
        },
        (error: any) => {
          console.error('Error saving grades:', error);
        }
      );
    }
  }
  
  
  
}
