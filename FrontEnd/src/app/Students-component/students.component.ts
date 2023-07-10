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
        this.students = students.filter(student => student.team_id === 1);
      }
    );
  }

  openDialog(studentId: number) {
    const dialogRef = this.matDialog.open(DialogBoxComponent, {
      data: { studentId }
    });
}



addStudent(studentName: string) {
  const email = studentName.toLowerCase() + '@gmail.com';
  const id = 1;
  const newStudent: Student = {
    name: studentName,
    id: 12,
    email: email,
    team_id: id,
    leader: false,
  };

  this.studentService.createStudent(newStudent).subscribe(
    (createdStudent: any) => {
      this.students.push(createdStudent);

      // Add the student to the team with ID 1
      this.studentService.addToTeam(createdStudent.id, 1).subscribe(
        (updatedStudent: Student) => {
          console.log(`Student ${updatedStudent.name} added to team.`);
        },
        (error: any) => {
          console.error('Failed to add student to team:', error);
        }
      );
    },
    (error: any) => {
      // Handle error if necessary
    }
  );
}



    deleteStudent(student: Student) {
      this.studentService.deleteStudent(student.id, 1).subscribe(
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

  
