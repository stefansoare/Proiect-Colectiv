import { Injectable } from '@angular/core';
import { Observable, of ,throwError} from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Task} from '../Classes/Task';
import { Student } from '../Classes/Student';

@Injectable({
  providedIn: 'root'
})
export class TasksService {
  private studentsUrl = 'http://localhost:8080/api/students/';
  private tasksUrl = 'http://localhost:8080/api/tasks/';
  constructor(private http: HttpClient) { }
  

  getTasks(): Observable<Task[]> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    return this.http.get<Task[]>(this.tasksUrl);
  }

  createTask(task: Task): Observable<Task> {
    return this.http.post<Task>(this.tasksUrl, task);
  }
  
  getStudent(studentId: number): Observable<Student> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.studentsUrl}id/${studentId}`;
  
    return this.http.get(url, { headers, observe: 'response' }).pipe(
      map(response => {
        const student = response.body as Student;
        return student;
      }),
      catchError(error => {
        if (error.status === 302 && error.error) {
          const student = error.error as Student;
          return of(student);
        } else {
          console.error(error);
          throw error;
        }
      })
    );
  }

  getTasksByActivity(aId: number): Observable<Task[]> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.tasksUrl}activities/${aId}`;
    return this.http.get<Task[]>(url, { headers });
  }
  
  getAllTasksOfAStudent(sId: number): Observable<Task[]> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.tasksUrl}${sId}/alltasks`;
    return this.http.get<Task[]>(url, { headers });
  }

  getTask(taskId: number): Observable<Task> {
    const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    const url = `${this.tasksUrl}id/${taskId}`;
  
    return this.http.get(url, { headers, observe: 'response' }).pipe(
      map(response => {
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
  patchTask(id: number, taskDTO: Task): Observable<Task> {
    const url = `${this.tasksUrl}${id}`;
    return this.http.patch<Task>(url, taskDTO);
  }
}
