import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';

@Component({
  selector: 'app-student-detail',
  templateUrl: './student-detail.component.html',
  styleUrls: ['./student-detail.component.css']
})
export class StudentDetailComponent implements OnInit {
  student: Student | undefined;

  constructor(
    private route: ActivatedRoute,
    private studentService: StudentService
  ) {}

  ngOnInit() {
    const studentId = Number(this.route.snapshot.paramMap.get('id'));
    this.getStudent(studentId);
  }

  getStudent(studentId: number) {
    this.studentService.getStudent(studentId)
      .subscribe(
        student => this.student = student,
        error => {
          console.error(error);
          // Handle the error case when the request returns a 302 status code
          if (error.status === 302 && error.error) {
            this.student = error.error;
          }
        }
      );
  }
}
