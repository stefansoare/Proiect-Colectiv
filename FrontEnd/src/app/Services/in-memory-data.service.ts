import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Student } from '../Classes/Student';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const students = [
      { id: 12, name: 'Antonio', email: 'antonio@yahoo.com', lider: true, TeamID: 1 },
      { id: 13, name: 'John', email: 'john@yahoo.com', lider: false, TeamID: 1  },
      { id: 14, name: 'Alex', email: 'alex@yahoo.com', lider: false, TeamID: 1  },
      { id: 15, name: 'Marcus', email: 'marcus@yahoo.com', lider: false, TeamID: 1  }
    
    ];
    return {students};
  }

  
  genId(students: Student[]): number {
    return students.length > 0 ? Math.max(...students.map(student => student.id)) + 1 : 11;
  }
}