import { Component, OnInit } from '@angular/core';
import { Student } from "../Classes/Student";
import { StudentService } from '../Services/student.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { DialogBoxComponent } from '../dialog-box/dialog-box.component';



@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})

export class StudentsComponent {
  students: Student[] = [];

  constructor(private studentService: StudentService,private http: HttpClient, public matDialog : MatDialog) { }



  ngOnInit() {
    this.studentService.getStudents().subscribe(
      students => {
        this.students = students.filter(student => student.teamID === 1);
      }
    );
  }

  openDialog(studentId: number) {
    const dialogRef = this.matDialog.open(DialogBoxComponent, {
      data: { studentId }
    });
}



  addStudent(studentName: string) {
    const newStudent: Student = {
      name: studentName,
      id: 0,
      email: '',
      leader: false,
      teamID: 0
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
      this.studentService.deleteStudent(student.id).subscribe(
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

  
