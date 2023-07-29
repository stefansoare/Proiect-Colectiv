import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MentorService } from './mentor.service';
import { Mentor } from '../Classes/Mentor';
import { Team } from '../Classes/Team';
import { Student } from '../Classes/Student';

describe('MentorService', () => {
  let service: MentorService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MentorService]
    });
    service = TestBed.inject(MentorService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get mentors', () => {
    const mockResponse: Mentor[] = [
      { id: 1, name: 'Mentor 1', email: 'mentor1@example.com' },
      { id: 2, name: 'Mentor 2', email: 'mentor2@example.com' }
    ];

    service.getMentors().subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/mentors/`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should create mentor', () => {
    const mentor: Mentor = { id: 1, name: 'Mentor 1', email: 'mentor1@example.com' };
    const mockResponse: Mentor = { id: 1, name: 'Mentor 1', email: 'mentor1@example.com' };

    service.createMentor(mentor).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/mentors/`);
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);
  });

  it('should get mentor by id', () => {
    const mentorId = 1;
    const mockResponse: Mentor = { id: 1, name: 'Mentor 1', email: 'mentor1@example.com' };

    service.getMentor(mentorId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/mentors/id/${mentorId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should delete mentor by id', () => {
    const mentorId = 1;
  
    service.deleteMentor(mentorId).subscribe((response) => {
      expect(response).toBeNull(); 
    });
  
    const request = httpMock.expectOne(`http://localhost:8080/api/mentors/id/${mentorId}`);
    expect(request.request.method).toBe('DELETE');
    request.flush(null); 
  });
  

  it('should get teams', () => {
    const mockResponse: Team[] = [
      { id: 1, name: 'Team 1', students: [], leader: '', mentorID: 1, activityID: 1, status: 1 },
      { id: 2, name: 'Team 2', students: [], leader: '', mentorID: 2, activityID: 2, status: 1 }
    ];

    service.getTeams().subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/teams/`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should get team members', () => {
    const tId = 1;
    const mockResponse: Student[] = [
      { id: 1, name: 'Student 1', email: 'student1@example.com', leader: true, team_id: 1 },
      { id: 2, name: 'Student 2', email: 'student2@example.com', leader: false, team_id: 1 }
    ];

    service.getTeamMembers(tId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/students/${tId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });
});