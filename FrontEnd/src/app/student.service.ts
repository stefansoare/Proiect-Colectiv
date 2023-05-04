import { Injectable } from '@angular/core';
import { Student } from './Student.1';
import { STUDENTS } from './mock-students';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
@Injectable({
  providedIn: 'root'
})
export class StudentService {
 
  getStudents(): Observable<Student[]> {
    const students = of(STUDENTS);
    this.messageService.add('StudentService: fetched students');
    return students;
  }

  constructor(private messageService: MessageService) { }

}
