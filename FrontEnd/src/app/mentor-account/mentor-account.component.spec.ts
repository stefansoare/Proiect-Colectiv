import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import { MentorPageComponent } from '../mentor-page/mentor-page.component';
import { MentorAccountComponent } from './mentor-account.component';
import { MentorListComponent } from '../mentor-list/mentor-list.component';
import { MentorService } from '../Services/mentor.service';
import { Mentor } from '../Classes/Mentor';
import { of, throwError } from 'rxjs';
import { SidebarMentorComponent } from '../Sidebars/sidebar-mentor/sidebar-mentor.component';

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




describe('MentorAccountComponent', () => {
  let component: MentorAccountComponent;
  let fixture: ComponentFixture<MentorAccountComponent>;
  let mockMentorService: MockMentorService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, FormsModule], 
      declarations: [MentorAccountComponent, SidebarMentorComponent],
      providers: [
        { provide: MentorService, useClass: MockMentorService }
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MentorAccountComponent);
    component = fixture.componentInstance;
    mockMentorService = TestBed.inject(MentorService) as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load mentors on component initialization', () => {
    expect(component.mentors.length).toBe(2);
  });
});
