import { Component } from '@angular/core';
import { GRADES } from '../mock-students';
import { Student } from '../Student.1'
import { Location } from '@angular/common';

@Component({
  selector: 'app-grades',
  templateUrl: './grades.component.html',
  styleUrls: ['./grades.component.css']
})
export class GradesComponent {

    students = GRADES;

    constructor(
      private location: Location
    ) {}

    goBack(): void {
      this.location.back();
    }
    names: string[] = ['John', 'Jane', 'Alice', 'Bob'];
}
