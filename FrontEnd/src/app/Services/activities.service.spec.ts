import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ActivitiesService } from './activities.service';
import { Activity } from '../Classes/Activity';
import { Team } from '../Classes/Team';

describe('ActivitiesService', () => {
  let service: ActivitiesService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ActivitiesService]
    });
    service = TestBed.inject(ActivitiesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should assign activity to team', () => {
    const teamId = 1;
    const activityId = 1;
    const mockResponse: Team = {
      id: 1,
      name: 'Team 1',
      students: [],
      leader: 'Leader 1',
      mentorID: 123,
      activityID: 456,
      status: 0
    };

    service.assignActivity(teamId, activityId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/teams/${teamId}/activities/${activityId}`);
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);
  });

  it('should get activities', () => {
    const mockResponse: Activity[] = [
      { id: 1, name: 'Activity 1', description: 'Description 1' },
      { id: 2, name: 'Activity 2', description: 'Description 2' }
    ];

    service.getActivities().subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne('http://localhost:8080/api/activities/');
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should create activity', () => {
    const mockActivity: Activity = { id: 1, name: 'Activity 1', description: 'Description 1' };
    const mockResponse: Activity = { id: 1, name: 'Activity 1', description: 'Description 1' };

    service.createActivity(mockActivity).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne('http://localhost:8080/api/activities/');
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);
  });

  it('should get activity by ID', () => {
    const activityId = 1;
    const mockResponse: Activity = { id: activityId, name: 'Activity 1', description: 'Description 1' };

    service.getActivity(activityId).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/activities/id/${activityId}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);
  });

  it('should delete activity', () => {
    const activityId = 1;

    service.deleteMentor(activityId).subscribe((response) => {
      expect(response).toBeUndefined();
    });

    const request = httpMock.expectOne(`http://localhost:8080/api/activities/id/${activityId}`);
    expect(request.request.method).toBe('DELETE');
    request.flush({});
  });
});
