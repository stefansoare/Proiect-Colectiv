import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';

import { DashboardComponent } from './dashboard.component';
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';
import { SidebarStudentComponent } from '../Sidebars/sidebar-student/sidebar-student.component';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let studentService: jasmine.SpyObj<StudentService>;

  const mockStudents: Student[] = [
    {
      id: 1,
      name: 'John Doe',
      email: 'john@example.com',
      leader: true,
      team_id: 1,
    },
    {
      id: 2,
      name: 'Jane Smith',
      email: 'jane@example.com',
      leader: false,
      team_id: 1,
    },
  ];

  const mockError = {
    status: 302,
    error: {
      id: jasmine.any(Number) as unknown as number,
      name: jasmine.any(String) as unknown as string,
      email: jasmine.any(String) as unknown as string,
      leader: jasmine.any(Boolean) as unknown as boolean,
      team_id: jasmine.any(Number) as unknown as number,
    },
  };

  beforeEach(async () => {
    const studentServiceSpy = jasmine.createSpyObj('StudentService', ['getStudents', 'getStudent']);

    studentServiceSpy.getStudents.and.returnValue(of(mockStudents));
    studentServiceSpy.getStudent.and.returnValue(of(mockStudents[0])); // or throwError(mockError) for error handling

    await TestBed.configureTestingModule({
      declarations: [DashboardComponent, SidebarStudentComponent],
      providers: [{ provide: StudentService, useValue: studentServiceSpy }],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    studentService = TestBed.inject(StudentService) as jasmine.SpyObj<StudentService>;
  });

  beforeEach(() => {
    spyOn(console, 'error');
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch students on component initialization', () => {
    // Arrange
    studentService.getStudents.and.returnValue(of(mockStudents));

    // Act
    fixture.detectChanges();

    // Assert
    expect(component.students).toEqual(mockStudents);
  });

  it('should handle successful student retrieval', () => {
    // Arrange
    studentService.getStudent.and.returnValue(of(mockStudents[0]));

    // Act
    fixture.detectChanges();

    // Assert
    expect(component.student).toEqual(mockStudents[0]);
  });

  it('should handle error during student retrieval', () => {
    // Arrange
    studentService.getStudent.and.returnValue(throwError(mockError));

    // Act
    fixture.detectChanges();

    // Assert
    expect(component.student).toEqual(mockError.error);
    expect(console.error).toHaveBeenCalledWith(mockError);
  });
});
