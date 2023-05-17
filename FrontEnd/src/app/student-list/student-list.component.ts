import { Component, OnInit } from '@angular/core';
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';
import { Student1 } from '../Classes/student1'

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit{

  students: Student1[] | undefined ;

  constructor(private studentService: StudentService) {
  }

  ngOnInit() {
    this.studentService.findAll().subscribe(data => {
      this.students = data;
    });
  }

}
