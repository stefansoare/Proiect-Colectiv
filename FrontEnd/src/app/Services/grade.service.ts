import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';

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

}
