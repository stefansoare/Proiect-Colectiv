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
  route: any;

  constructor(private studentService: StudentService) {}

  ngOnInit(): void {
    this.getStudent();
  }

  getStudent(): void {
    const email = String(this.route.snapshot.paramMap.get('email'));
    this.studentService.getStudentByEmail(email).subscribe(
      (student: Student | null) => {
        this.student = student ? student : undefined;
      }
    );
  }

}
