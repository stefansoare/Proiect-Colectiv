import { Injectable } from '@angular/core';
import { Student } from './Student.1';
import { STUDENTS } from './mock-students';
import { Observable, of } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class StudentService {
 
  getStudents(): Observable<Student[]> {
    const students = of(STUDENTS);
    return students;
  }
  constructor() { }
  
}
