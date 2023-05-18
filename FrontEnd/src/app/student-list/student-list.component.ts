import { Component, OnInit } from '@angular/core';
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {
  students: Student[] = [];

  constructor(private studentService: StudentService) { }

  ngOnInit() {
    this.studentService.getStudents().subscribe(
      students => this.students = students
    );
  }
}
