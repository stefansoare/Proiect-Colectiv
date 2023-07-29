import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MentorListComponent } from './mentor-list.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import { MentorPageComponent } from '../mentor-page/mentor-page.component';
import { MentorService } from '../Services/mentor.service';
import { Mentor } from '../Classes/Mentor';
import { of, throwError } from 'rxjs';
import { SidebarMentorComponent } from '../Sidebars/sidebar-mentor/sidebar-mentor.component';
describe('MentorListComponent', () => {
  let component: MentorListComponent;
  let fixture: ComponentFixture<MentorListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, FormsModule], 
      declarations: [MentorListComponent, SidebarMentorComponent],
      providers: [MentorService],
    }).compileComponents();

    fixture = TestBed.createComponent(MentorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

