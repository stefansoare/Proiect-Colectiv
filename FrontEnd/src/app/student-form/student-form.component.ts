import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StudentService } from '../Services/student.service';
import { Student1 } from '../Classes/student1';

@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent {

  student: Student1 ;

  constructor(
    private route: ActivatedRoute, 
      private router: Router, 
        private studentService: StudentService) {
    this.student = new Student1();
  }

  onSubmit() {
    this.studentService.save(this.student).subscribe(result => this.gotoStudentList());
  }

  gotoStudentList() {
    this.router.navigate(['/students']);
  }

}
