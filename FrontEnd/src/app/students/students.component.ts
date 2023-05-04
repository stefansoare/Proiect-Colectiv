import { Component, OnInit } from '@angular/core';
import { Student } from "../Student.1";
import { STUDENTS } from '../mock-students';
import { StudentService } from '../student.service';
import { MessageService } from '../message.service';

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
  this.messageService.add(`HeroesComponent: Selected hero id=${student.id}`);
}  
  
constructor(private studentService: StudentService, private messageService: MessageService ) {}

ngOnInit(): void {
  this.getStudents();
}

getStudents(): void {
  this.studentService.getStudents()
      .subscribe(students => this.students = students);
}

}