import { Component, Input } from '@angular/core';
import { Student } from '../Student.1';
import { STUDENTS } from '../mock-students';
@Component({
  selector: 'app-student-detail',
  templateUrl: './student-detail.component.html',
  styleUrls: ['./student-detail.component.css']
})
export class StudentDetailComponent {

  @Input() student?: Student;

}
