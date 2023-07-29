import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MentorPageComponent } from './mentor-page.component';
import { MatTableDataSource } from '@angular/material/table';
import { StudentService } from '../Services/student.service';
import { ActivitiesService } from '../Services/activities.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { Activity } from '../Classes/Activity';
import { SidebarMentorComponent } from '../Sidebars/sidebar-mentor/sidebar-mentor.component'; // Import the SidebarMentorComponent

describe('MentorPageComponent', () => {
  let component: MentorPageComponent;
  let fixture: ComponentFixture<MentorPageComponent>;

  const studentServiceMock = {
    getStudents: () => of([]),
  };

  const activitiesServiceMock = {
    getActivities: () => of([]) as any,
  };

  const routerMock = {
    navigate: jasmine.createSpy('navigate'),
  };

  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        declarations: [MentorPageComponent, SidebarMentorComponent], // Add SidebarMentorComponent to the declarations
        providers: [
          { provide: StudentService, useValue: studentServiceMock },
          { provide: ActivitiesService, useValue: activitiesServiceMock },
          { provide: Router, useValue: routerMock },
        ],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(MentorPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the MentorPageComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should toggle showTable value when calling toggleTableVisibility()', () => {
    expect(component.showTable).toBe(false);
    component.toggleTableVisibility();
    expect(component.showTable).toBe(true);
    component.toggleTableVisibility();
    expect(component.showTable).toBe(false);
  });

  it('should initialize dataSource with empty activities array', () => {
    expect(component.dataSource.data).toEqual([]);
  });

  it('should update dataSource when activities are fetched', () => {
    const activityData: Activity[] = [
      { id: 1, name: 'Activity 1', description: '' },
      { id: 2, name: 'Activity 2', description: '' },
    ];

    spyOn(activitiesServiceMock, 'getActivities').and.returnValue(of(activityData) as any);

    component.getActivities();

    expect(component.dataSource.data).toEqual(activityData);
  });

  it('should navigate to the specified component name', () => {
    const activityId = 1;
    component.navigateToDetails(activityId);
    expect(routerMock.navigate).toHaveBeenCalledWith(['activity', activityId]);
  });
});
