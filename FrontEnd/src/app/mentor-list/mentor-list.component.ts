import { Component, OnInit } from '@angular/core';
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';

@Component({
  selector: 'app-mentor-list',
  templateUrl: './mentor-list.component.html',
  styleUrls: ['./mentor-list.component.css']
})
export class MentorListComponent implements OnInit {
  students: Student[] = [];
  filteredStudents: Student[] = [];
  searchText: string = '';

  constructor(private studentService: StudentService) { }

  ngOnInit() {
    this.studentService.getStudents().subscribe(
      students => {
        this.students = students;
        this.filteredStudents = students;
      }
    );
  }

  filterStudents() {
    if (!this.searchText) {
      this.filteredStudents = this.students; 
    } else {
      this.filteredStudents = this.students.filter(student =>
        student.name.toLowerCase().includes(this.searchText.toLowerCase())
      );
    }
  }
}
