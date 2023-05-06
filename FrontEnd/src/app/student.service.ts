import { Injectable } from '@angular/core';
import { Student } from './Student.1';
import { STUDENTS } from './mock-students';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class StudentService {
 
  private studentsUrl = 'api/students';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  
  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.studentsUrl)
      .pipe(
        tap(_ => this.log('fetched students')),
        catchError(this.handleError<Student[]>('getStudents', []))
      );
  }

  /**
 * Handle Http operation that failed.
 * Let the app continue.
 *
 * @param operation - name of the operation that failed
 * @param result - optional value to return as the observable result
 */
private handleError<T>(operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {

    // TODO: send the error to remote logging infrastructure
    console.error(error); // log to console instead

    // TODO: better job of transforming error for user consumption
    this.log(`${operation} failed: ${error.message}`);

    // Let the app keep running by returning an empty result.
    return of(result as T);
  };
}

  constructor(private http: HttpClient,private messageService: MessageService) { }

 
  private log(message: string) {
  this.messageService.add(`StudentService: ${message}`);
}

getStudent(id: number): Observable<Student> {
  const url = `${this.studentsUrl}/${id}`;
  return this.http.get<Student>(url).pipe(
    tap(_ => this.log(`fetched student id=${id}`)),
    catchError(this.handleError<Student>(`getStudent id=${id}`))
  );
}

  
updateStudent(student: Student): Observable<any> {
  return this.http.put(this.studentsUrl, student, this.httpOptions).pipe(
    tap(_ => this.log(`updated student id=${student.id}`)),
    catchError(this.handleError<any>('updateStudent'))
  );

  
}


addStudent(student: Student): Observable<Student> {
  return this.http.post<Student>(this.studentsUrl, student, this.httpOptions).pipe(
    tap((newStudent: Student) => this.log(`added student w/ id=${newStudent.id}`)),
    catchError(this.handleError<Student>('addStudent'))
  );
}

deleteStudent(id: number): Observable<Student> {
  const url = `${this.studentsUrl}/${id}`;

  return this.http.delete<Student>(url, this.httpOptions).pipe(
    tap(_ => this.log(`deleted student id=${id}`)),
    catchError(this.handleError<Student>('deleteStudent'))
  );
}

searchStudents(term: string): Observable<Student[]> {
  if (!term.trim()) {
    return of([]);
  }
  return this.http.get<Student[]>(`${this.studentsUrl}/?name=${term}`).pipe(
    tap(x => x.length ?
       this.log(`found students matching "${term}"`) :
       this.log(`no students matching "${term}"`)),
    catchError(this.handleError<Student[]>('searchStudents', []))
  );
}

}
