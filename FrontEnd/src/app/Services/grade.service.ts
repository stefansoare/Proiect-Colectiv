import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map } from 'rxjs';
import { Grade } from '../Classes/Grade';

@Injectable({
  providedIn: 'root'
})
export class GradeService {
  

  constructor(private http: HttpClient) { }
  getStudentMeanGrade(tId: number, sId: number): Observable<number> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `http://localhost:8080/api/grades/mean/${tId}/for/${sId}`;
    return this.http.get<number>(url, { headers });
  }

  giveGrades(mId: number, sId: number, tId: number, grades: Grade[]): Observable<HttpResponse<Grade[]>> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `http://localhost:8080/api/grades/from/${mId}/to/${sId}/for/${tId}`;
    return this.http.post<Grade[]>(url, grades, { headers, observe: 'response' });
  }

  getAllStudentAttendances(sId: number): Observable<any> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `http://localhost:8080/api/grades/attendance/${sId}`;
    return this.http.get<any>(url, { headers });
  }

  getAllStudentGradesFromATask(tId: number, sId: number): Observable<number> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `http://localhost:8080/api/grades/grades/${tId}/for/${sId}`;
    return this.http.get<any>(url, { headers }).pipe(
      map((response: any) => response.grade) // Extract the 'grade' property from the response
    );
  }
  

  getAttendanceForTask(tId: number, sId: number): Observable<any> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `http://localhost:8080/api/grades/task/${tId}/attendance/student/${sId}`;
    return this.http.get<any>(url, { headers });
  }
  
  

}
