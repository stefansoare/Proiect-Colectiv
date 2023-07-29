import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TasksService } from './tasks.service';
import { Task } from '../Classes/Task';
import { Student } from '../Classes/Student';

describe('TasksService', () => {
  let service: TasksService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TasksService]
    });
    service = TestBed.inject(TasksService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get tasks', () => {
    const mockResponse: Task[] = [
      { id: 1, deadline: '2023-07-31', description: 'Task 1', activity_id: 1, status_id: 1 },
      { id: 2, deadline: '2023-08-15', description: 'Task 2', activity_id: 1, status_id: 2 }
    ];

    service.getTasks().subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/tasks/`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should create task', () => {
    const task: Task = { id: 1, deadline: '2023-07-31', description: 'Task 1', activity_id: 1, status_id: 1 };
    const mockResponse: Task = { id: 1, deadline: '2023-07-31', description: 'Task 1', activity_id: 1, status_id: 1 };

    service.createTask(task).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/tasks/`);
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);
  });

  it('should get student by id', () => {
    const studentId = 1;
    const mockResponse: Student = { id: 1, name: 'Student 1', email: 'student1@example.com', leader: true, team_id: 1 };

    service.getStudent(studentId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/students/id/${studentId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should get tasks by activity id', () => {
    const activityId = 1;
    const mockResponse: Task[] = [
      { id: 1, deadline: '2023-07-31', description: 'Task 1', activity_id: 1, status_id: 1 },
      { id: 2, deadline: '2023-08-15', description: 'Task 2', activity_id: 1, status_id: 2 }
    ];

    service.getTasksByActivity(activityId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/tasks/activities/${activityId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should get task by id', () => {
    const taskId = 1;
    const mockResponse: Task = { id: 1, deadline: '2023-07-31', description: 'Task 1', activity_id: 1, status_id: 1 };

    service.getTask(taskId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/tasks/id/${taskId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should delete task', () => {
    const taskId = 1;
  
    service.deleteTask(taskId).subscribe((response) => {
      expect(response).toBeNull(); // Change the expectation to be toBeNull()
    });
  
    const request = httpMock.expectOne(`http://localhost:8080/api/tasks/${taskId}`);
    expect(request.request.method).toBe('DELETE');
    request.flush(null); // The response body is set to null since there is no content.
  });
  
  

  it('should patch task', () => {
    const taskId = 1;
    const taskDTO: Task = { id: 1, deadline: '2023-07-31', description: 'Task 1 Updated', activity_id: 1, status_id: 1 };
    const mockResponse: Task = { id: 1, deadline: '2023-07-31', description: 'Task 1 Updated', activity_id: 1, status_id: 1 };

    service.patchTask(taskId, taskDTO).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/tasks/${taskId}`);
    expect(request.request.method).toBe('PATCH');
    request.flush(mockResponse);
  });
});
