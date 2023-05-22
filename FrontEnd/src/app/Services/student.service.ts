import { Injectable } from '@angular/core';
import { Student } from '../Classes/Student';
import { Team } from '../Classes/Team';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import {Express}  from 'express';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
 
  private studentsUrl = 'http://localhost:8080/api/students/';
  private teamsUrl = 'http://localhost:8080/api/teams/';
  private teamMembersUrl= 'http://localhost:8080/api/students/';
  constructor(private http: HttpClient) { }
  

  getStudents(): Observable<Student[]> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    return this.http.get<Student[]>(this.studentsUrl);
  }

  createStudent(student: Student): Observable<Student> {
    return this.http.post<Student>(this.studentsUrl, student);
  }
  getStudent(studentId: number): Observable<Student> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.studentsUrl}id/${studentId}`;
  
    return this.http.get(url, { headers, observe: 'response' }).pipe(
      map(response => {
        // Extract the student details from the response body
        const student = response.body as Student;
        return student;
      }),
      catchError(error => {
        console.error(error);
        throw error;
      })
    );
  }

  deleteStudent(studentId: number): Observable<void> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.studentsUrl}id/${studentId}`;
  
    return this.http.delete<void>(url, { headers });
  }
  getTeams(): Observable<Team[]> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    return this.http.get<Team[]>(this.teamsUrl);
  }

  getTeamMembers(tId: number): Observable<Student[]> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    return this.http.get<Student[]>(`${this.teamMembersUrl}${tId}`, { headers });
  }
}
