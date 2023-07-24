import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { GradeService } from './grade.service';
import { Grade } from '../Classes/Grade';

describe('GradeService', () => {
  let service: GradeService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [GradeService]
    });
    service = TestBed.inject(GradeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get student mean grade', () => {
    const tId = 1;
    const sId = 1;
    const mockResponse: number = 85;

    service.getStudentMeanGrade(tId, sId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/grades/mean/${tId}/for/${sId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should give grades to students', () => {
    const mId = 1;
    const sId = 2;
    const tId = 3;
    const grades: Grade[] = [
      { comment: 'Good work!', grade: 90, attendance: true },
      { comment: 'Excellent!', grade: 95, attendance: true }
    ];
    const mockResponse: Grade[] = [
      { comment: 'Good work!', grade: 90, attendance: true },
      { comment: 'Excellent!', grade: 95, attendance: true }
    ];

    service.giveGrades(mId, sId, tId, grades).subscribe((response) => {
      expect(response.body).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/grades/from/${mId}/to/${sId}/for/${tId}`);
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);
  });

  it('should get all student attendances', () => {
    const sId = 1;
    const mockResponse: any = [
      { date: '2023-07-20', status: 'Present' },
      { date: '2023-07-21', status: 'Absent' }
    ];

    service.getAllStudentAttendances(sId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/grades/attendance/${sId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should get all student grades from a task', () => {
    const tId = 1;
    const sId = 2;
    const mockResponse: number = 85;

    service.getAllStudentGradesFromATask(tId, sId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/grades/grades/${tId}/for/${sId}`);
    expect(request.request.method).toBe('GET');
    request.flush({ grade: mockResponse });
  });

  it('should get attendance for task', () => {
    const tId = 1;
    const sId = 2;
    const mockResponse: any = [
      { date: '2023-07-20', status: 'Present' },
      { date: '2023-07-21', status: 'Absent' }
    ];

    service.getAttendanceForTask(tId, sId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/grades/task/${tId}/attendance/student/${sId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });
});
