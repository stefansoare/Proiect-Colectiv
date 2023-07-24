import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatTableModule } from '@angular/material/table';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { MentorPageComponent } from './mentor-page.component';
import { MentorAccountComponent } from '../mentor-account/mentor-account.component';
import { MentorListComponent } from '../mentor-list/mentor-list.component';
import { MentorService } from '../Services/mentor.service';
import { SidebarMentorComponent } from '../Sidebars/sidebar-mentor/sidebar-mentor.component';
import { ActivitiesService } from '../Services/activities.service';
import { StudentService } from '../Services/student.service';
import { of } from 'rxjs';
import { Student } from '../Classes/Student';
import { Team } from '../Classes/Team';
import { Activity } from '../Classes/Activity';

class MockMentorService {
  getMentors() {
    return of([
      { id: 1, name: 'Mentor 1', email: 'mentor1@example.com' },
      { id: 2, name: 'Mentor 2', email: 'mentor2@example.com' }
    ]);
  }

  getMentor(id: number) {
    return of({ id: 1, name: 'Mentor 1', email: 'mentor1@example.com' });
  }
}

describe('MentorPageComponent', () => {
  let component: MentorPageComponent;
  let fixture: ComponentFixture<MentorPageComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        MatExpansionModule,
        MatTableModule,
        BrowserAnimationsModule,
        FormsModule,
        HttpClientTestingModule
      ],
      declarations: [MentorPageComponent, MentorListComponent, MentorAccountComponent, SidebarMentorComponent],
      providers: [ActivitiesService, StudentService, MentorService] // Add any additional services here
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MentorPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should toggle table visibility when toggleTableVisibility is called', () => {
    
    expect(component.showTable).toBeFalse();

    component.toggleTableVisibility();
    expect(component.showTable).toBeTrue();

    component.toggleTableVisibility();
    expect(component.showTable).toBeFalse();
  });

  it('should navigate to the specified component when navigateToComponent is called', () => {
    const routerSpy = spyOn(component['router'], 'navigate');

    const componentName = 'some-component';
    component.navigateToComponent(componentName);

    expect(routerSpy).toHaveBeenCalledWith([componentName]);
  });
});
