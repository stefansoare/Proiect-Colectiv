import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Activity} from '../Classes/Activity';
import { Team } from '../Classes/Team';

@Injectable({
  providedIn: 'root'
})
export class ActivitiesService {
 
  private activitiesUrl = 'http://localhost:8080/api/activities/';
  private teamsUrl = 'http://localhost:8080/api/teams/';
  constructor(private http: HttpClient) { }
  

  assignActivity(id: number, aId: number): Observable<Team> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `http://localhost:8080/api/teams/${id}/activities/${aId}`;
    return this.http.post<Team>(url, null, { headers });
  }

  getActivities(): Observable<Activity[]> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    return this.http.get<Activity[]>(this.activitiesUrl);
  }

  createActivity(activity: Activity): Observable<Activity> {
    return this.http.post<Activity>(this.activitiesUrl, activity);
  }
  getActivity(activityId: number): Observable<Activity> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.activitiesUrl}id/${activityId}`;
  
    return this.http.get(url, { headers, observe: 'response' }).pipe(
      map(response => {
        // Extract the student details from the response body
        const activity = response.body as Activity;
        return activity;
      }),
      catchError(error => {
        console.error(error);
        throw error;
      })
    );
  }

  deleteMentor(activityId: number): Observable<void> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.activitiesUrl}id/${activityId}`;
  
    return this.http.delete<void>(url, { headers });
  }
}
