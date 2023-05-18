import { Component, OnInit } from '@angular/core';
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-student-detail',
  templateUrl: './student-detail.component.html',
  styleUrls: ['./student-detail.component.css']
})
export class StudentDetailComponent implements OnInit {
  student: Student | null=null;

  constructor(
    private studentService: StudentService,
    private route: ActivatedRoute // Inject ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getStudent();
  }

  getStudent(): void {
    this.studentService.getStudents().subscribe((students: Student[]) => {
      this.student = students[0]; // Assign the first student
    });
  }
}
