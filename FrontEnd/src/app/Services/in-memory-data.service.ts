import { Injectable } from '@angular/core';
import { Student } from '../Classes/Student';
import { Pool, QueryResult } from 'pg';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService {
<<<<<<< Updated upstream
  private pool: Pool;

  constructor() {
    // Configure your PostgreSQL connection details
    this.pool = new Pool({
      host: 'localhost:5432',
      user: 'postgres',
      password: 'password',
      database: 'PC',
      port: 5432, // Adjust the port if necessary
    });
  }
=======
  
>>>>>>> Stashed changes

  async getAllStudents(): Promise<Student[]> {
    try {
      const queryResult: QueryResult = await this.pool.query('SELECT * FROM students');
      return queryResult.rows as Student[];
    } catch (error) {
      console.error('Error retrieving students from the database:', error);
      return [];
    }
  }

  async getStudentById(id: number): Promise<Student | null> {
    try {
      const queryResult: QueryResult = await this.pool.query('SELECT * FROM students WHERE id = $1', [id]);
      const student: Student | null = queryResult.rows[0] || null;
      return student;
    } catch (error) {
      console.error(`Error retrieving student with ID ${id} from the database:`, error);
      return null;
    }
  }

  async genId(): Promise<number> {
    try {
      const queryResult: QueryResult = await this.pool.query('SELECT MAX(id) as max_id FROM students');
      const maxId: number = queryResult.rows[0].max_id || 0;
      return maxId + 1;
    } catch (error) {
      console.error('Error generating ID from the database:', error);
      return 11; // Default ID if there is an error
    }
  }
}
