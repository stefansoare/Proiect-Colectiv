import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Task} from '../Classes/Task';

@Injectable({
  providedIn: 'root'
})
export class TasksService {
 
  private tasksUrl = 'http://localhost:8080/api/tasks/';
  constructor(private http: HttpClient) { }
  

  getTasks(): Observable<Task[]> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    return this.http.get<Task[]>(this.tasksUrl);
  }

  createTask(task: Task): Observable<Task> {
    return this.http.post<Task>(this.tasksUrl, task);
  }
  getTasksByActivity(aId: number): Observable<Task[]> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.tasksUrl}activities/${aId}`; // Update the URL with the activity ID
  
    return this.http.get<Task[]>(url, { headers });
  }
  

  getTask(taskId: number): Observable<Task> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.tasksUrl}id/${taskId}`;
  
    return this.http.get(url, { headers, observe: 'response' }).pipe(
      map(response => {
        // Extract the student details from the response body
        const task = response.body as Task;
        return task;
      }),
      catchError(error => {
        console.error(error);
        throw error;
      })
    );
  }
  deleteTask(taskId: number): Observable<void> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.tasksUrl}${taskId}`;
  
    return this.http.delete<void>(url, { headers });
  }
}
