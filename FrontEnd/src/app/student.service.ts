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

  getStudent(id: number): Observable<Student>{
    const student = STUDENTS.find(h => h.id === id)!;
    this.messageService.add('StudentService: fetched stundet id=${id}');
    return of(student);
  }

}
