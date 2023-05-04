import { Component } from '@angular/core';
import { Student } from "../Student.1";
import { STUDENTS } from '../mock-students';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
export class StudentsComponent {
  students = STUDENTS;
  selectedStudent?: Student;
  onSelect(student: Student): void {
  this.selectedStudent = student;
}
}
