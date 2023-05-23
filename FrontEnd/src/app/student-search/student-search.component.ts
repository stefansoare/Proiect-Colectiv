import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';


@Component({
  selector: 'app-student-search',
  templateUrl: './student-search.component.html',
  styleUrls: [ './student-search.component.css' ]
})
export class StudentSearchComponent {
  students$!: Observable<Student[]>;
  private searchTerms = new Subject<string>();

  constructor(private studentService: StudentService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }
}