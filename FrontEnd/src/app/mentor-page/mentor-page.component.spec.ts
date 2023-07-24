import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MentorPageComponent } from './mentor-page.component';
import { MatTableDataSource } from '@angular/material/table';
import { StudentService } from '../Services/student.service';
import { ActivitiesService } from '../Services/activities.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { Activity } from '../Classes/Activity';

describe('MentorPageComponent', () => {
  let component: MentorPageComponent;
  let fixture: ComponentFixture<MentorPageComponent>;

 
  const studentServiceMock = {
    getStudents: () => of([]), 
  };

  const activitiesServiceMock = {
    getActivities: () => of([]), 
  };

  const routerMock = {
    navigate: jasmine.createSpy('navigate'),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MentorPageComponent],
      providers: [
        { provide: StudentService, useValue: studentServiceMock },
        { provide: ActivitiesService, useValue: activitiesServiceMock },
        { provide: Router, useValue: routerMock },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MentorPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the MentorPageComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with showTable set to false', () => {
    expect(component.showTable).toBeFalse();
  });

  it('should toggle showTable value when calling toggleTableVisibility()', () => {
    expect(component.showTable).toBeFalse();
    component.toggleTableVisibility();
    expect(component.showTable).toBeTrue();
    component.toggleTableVisibility();
    expect(component.showTable).toBeFalse();
  });

  it('should navigate to the specified component name', () => {
    const componentName = 'example-component';
    component.navigateToComponent(componentName);
    expect(routerMock.navigate).toHaveBeenCalledWith([componentName]);
  });

  it('should initialize dataSource with empty activities array', () => {
    expect(component.dataSource).toBeTruthy();
    expect(component.dataSource.data.length).toBe(0);
  });

  it('should update dataSource when activities are fetched', () => {
    const activities: Activity[] = [
      {id: 1, name: 'Activity 1', description: 'Description 1' },
      {id: 1, name: 'Activity 1', description: 'Description 1'},
    ];
  
    spyOn(activitiesServiceMock, 'getActivities').and.returnValue(of(activities as never[]));
  
    component.ngOnInit();
    expect(component.activities).toEqual(activities);
    expect(component.dataSource.data).toEqual(activities);
  });
});
