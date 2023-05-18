import { Component, OnInit } from '@angular/core';
import { Student } from "../Classes/Student";
import { StudentService } from '../Services/student.service';


@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})

export class StudentsComponent {
  students: Student[] = [];
  
  constructor(private studentService: StudentService){}



  menuItems = [
    { title: 'Home', path: '/home', icon: 'home', class: '' },
    { title: 'About', path: '/about', icon: 'info', class: '' },
    { title: 'Contact', path: '/contact', icon: 'email', class: '' }
  ];

 /* ngOnInit(): void {
    this.getStudents();
  }

  getStudents(): void{
    this.studentService.getStudents()
    .subscribe(students => this.students = students);
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.studentService.addStudent({ name } as Student)
      .subscribe(student => {
        this.students.push(student);
      });
  }

  delete(student: Student): void {
    this.students = this.students.filter(h => h !== student);
    this.studentService.deleteStudent(student.id).subscribe();
  }
 */
}  
  
