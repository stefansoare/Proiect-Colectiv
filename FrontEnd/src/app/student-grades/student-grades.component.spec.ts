import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing'; 

import { StudentGradesComponent } from './student-grades.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('StudentGradesComponent', () => {
  let component: StudentGradesComponent;
  let fixture: ComponentFixture<StudentGradesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentGradesComponent],
      imports: [HttpClientTestingModule],
      schemas: [
        NO_ERRORS_SCHEMA
      ] 
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentGradesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
