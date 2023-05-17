import { Component, Input, OnInit } from '@angular/core';
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent {
  students: Student[] = [];
  
   student: Student = {
    id: 1,
    name: 'John Doe',
    email: 'johndoe@example.com',
    lider: false,
    TeamID: 1
  };
  createDb() {
    const students = [
      { id: 12, name: 'Antonio', email: 'antonio@yahoo.com', lider: true, TeamID: 1 },
      { id: 13, name: 'John', email: 'john@yahoo.com', lider: false, TeamID: 1  },
      { id: 14, name: 'Alex', email: 'alex@yahoo.com', lider: false, TeamID: 1  },
      { id: 15, name: 'Marcus', email: 'marcus@yahoo.com', lider: false, TeamID: 1  }
    
    ];
    return {students};
  }
  


  constructor(private studentService: StudentService) { }

 /* ngOnInit(): void {
    this.getStudents();
  }
  
 

  getStudents(): void {
    this.studentService.getStudents()
      .subscribe(students => this.students = students.slice(1, 5));
  }
  

  
  genId(students: Student[]): number {
    return students.length > 0 ? Math.max(...students.map(student => student.id)) + 1 : 11;
  }*/
}