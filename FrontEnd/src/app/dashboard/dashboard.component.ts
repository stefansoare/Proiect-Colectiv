import { Component, Input, OnInit } from '@angular/core';
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent  {
  students: Student[] = [];
  student: Student | null = null;

  constructor(private studentService: StudentService) { }

  ngOnInit() {
    this.studentService.getStudents().subscribe(
      students => this.students = students
    );

    this.studentService.getStudent(1).subscribe(
      student => this.student = student,
      error => {
        console.error(error);
        // Handle error, show error message, etc.
        if (error.status === 302 && error.error) {
          this.student = error.error;
        }
      }
    );
  }
  
}