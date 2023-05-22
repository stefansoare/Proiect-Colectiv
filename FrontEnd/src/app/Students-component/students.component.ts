import { Component, OnInit } from '@angular/core';
import { Student } from "../Classes/Student";
import { StudentService } from '../Services/student.service';
import { HttpClient } from '@angular/common/http';



@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})

export class StudentsComponent {
  students: Student[] = [];

  constructor(private studentService: StudentService,private http: HttpClient) { }



  ngOnInit() {
    this.studentService.getStudents().subscribe(
      students => this.students = students
    );
  }


  addStudent(studentName: string) {
    const newStudent: Student = {
      name: studentName,
      id: 0,
      email: '',
      leader: false,
      TeamID: 0
    };
  
    this.studentService.createStudent(newStudent).subscribe(
      (createdStudent: any) => {
        this.students.push(createdStudent);
      },
      (error: any) => {
        // Handle error if necessary
      }
    );
    }
    deleteStudent(student: Student) {
      const studentID = student.id;
      this.http.delete(`http://localhost:8080/api/students/${studentID}`).subscribe(
        () => {
          const index = this.students.indexOf(student);
          if (index > -1) {
            this.students.splice(index, 1);
          }
        },
        (error: any) => {
          // Handle error if necessary
        }
      );
    }
    
    
    
    }
    
  
  
    
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

  
