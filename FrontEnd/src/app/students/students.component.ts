import { Component, OnInit } from '@angular/core';
import { Student } from "../Student.1";
import { STUDENTS } from '../mock-students';
import { StudentService } from '../student.service';
@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
export class StudentsComponent implements OnInit {
  selectedStudent?: Student;
  students: Student[] = [];
  onSelect(student: Student): void {
  this.selectedStudent = student;
}  
  

constructor(private studentService: StudentService) {}

ngOnInit(): void {
  this.getStudents();
}
getStudents(): void {
  this.studentService.getStudents()
      .subscribe(students => this.students = students);
}
}