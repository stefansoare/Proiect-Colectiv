import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StudentService } from './student.service';
import { Student } from '../Classes/Student';
import { Team } from '../Classes/Team';

describe('StudentService', () => {
  let service: StudentService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [StudentService]
    });
    service = TestBed.inject(StudentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get students', () => {
    const mockResponse: Student[] = [
      { id: 1, name: 'Student 1', email: 'student1@example.com', leader: true, team_id: 1 },
      { id: 2, name: 'Student 2', email: 'student2@example.com', leader: false, team_id: 1 }
    ];

    service.getStudents().subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/students/`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should add student to team', () => {
    const studentId = 1;
    const teamId = 1;
    const mockResponse: Student = { id: 1, name: 'Student 1', email: 'student1@example.com', leader: true, team_id: 1 };

    service.addToTeam(studentId, teamId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/students/1/teams/1`);
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);
  });

  it('should create student', () => {
    const student: Student = { id: 1, name: 'Student 1', email: 'student1@example.com', leader: true, team_id: 1 };
    const mockResponse: Student = { id: 1, name: 'Student 1', email: 'student1@example.com', leader: true, team_id: 1 };

    service.createStudent(student).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/students/`);
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

  it('should delete student from team', () => {
    const studentId = 1;
    const teamId = 1;

    service.deleteStudent(studentId, teamId).subscribe((response) => {
      expect(response).toBeUndefined();
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/students/1/teams/1`);
    expect(request.request.method).toBe('DELETE');
    request.flush({});
  });

  it('should get all teams', () => {
    const mockResponse: Team[] = [
      { id: 1, name: 'Team 1', students: [], leader: '', mentorID: 1, activityID: 1, status: 1 },
      { id: 2, name: 'Team 2', students: [], leader: '', mentorID: 2, activityID: 2, status: 1 }
    ];

    service.getAllTeams().subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/teams/`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should get team members', () => {
    const teamId = 1;
    const mockResponse: Student[] = [
      { id: 1, name: 'Student 1', email: 'student1@example.com', leader: true, team_id: 1 },
      { id: 2, name: 'Student 2', email: 'student2@example.com', leader: false, team_id: 1 }
    ];

    service.getTeamMembers(teamId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/students/${teamId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should get activity students', () => {
    const activityId = 1;
    const mockResponse: Student[] = [
      { id: 1, name: 'Student 1', email: 'student1@example.com', leader: true, team_id: 1 },
      { id: 2, name: 'Student 2', email: 'student2@example.com', leader: false, team_id: 2 }
    ];

    service.getActivityStudents(activityId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/students/activity/${activityId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });
});
