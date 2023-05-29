import { Injectable } from '@angular/core';
import { Student } from '../Classes/Student';
import { Team } from '../Classes/Team';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Mentor } from '../Classes/Mentor';

@Injectable({
  providedIn: 'root'
})
export class MentorService {
 
  private mentorsUrl = 'http://localhost:8080/api/mentors/';
  private teamsUrl = 'http://localhost:8080/api/teams/';
  private teamMembersUrl= 'http://localhost:8080/api/students/';
  constructor(private http: HttpClient) { }
  

  getMentors(): Observable<Mentor[]> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    return this.http.get<Mentor[]>(this.mentorsUrl);
  }

  createMentor(mentor: Mentor): Observable<Mentor> {
    return this.http.post<Student>(this.mentorsUrl, mentor);
  }
  getMentor(mentorId: number): Observable<Mentor> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.mentorsUrl}id/${mentorId}`;
  
    return this.http.get(url, { headers, observe: 'response' }).pipe(
      map(response => {
        // Extract the student details from the response body
        const mentor = response.body as Mentor;
        return mentor;
      }),
      catchError(error => {
        console.error(error);
        throw error;
      })
    );
  }
  getTeamStats(tId: number): Observable<number> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `http://localhost:8080/api/tasks/stats/${tId}`;
  
    return this.http.get<number>(url, { headers }).pipe(
      map(response => {
        // Extract the student stats from the response body
        const teamStats = response as number;
        return teamStats;
      }),
      catchError(error => {
        console.error(error);
        throw error;
      })
    );
  }
  deleteMentor(mentorId: number): Observable<void> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.mentorsUrl}id/${mentorId}`;
  
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
