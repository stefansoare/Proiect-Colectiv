import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { StudentService } from '../Services/student.service';
import { GradeService } from '../Services/grade.service';
import { GradingDialogComponent } from './grading-dialog.component';
import { Task } from '../Classes/Task';
import { Grade } from '../Classes/Grade';
import { of, throwError } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';

describe('GradingDialogComponent', () => {
  let component: GradingDialogComponent;
  let fixture: ComponentFixture<GradingDialogComponent>;
  let mockDialogRef: Partial<MatDialogRef<GradingDialogComponent>>;
  let mockStudentService: Partial<StudentService>;
  let mockGradeService: GradeService;

  beforeEach(async () => {
    mockDialogRef = {
      close: jasmine.createSpy('close')
    };

    mockStudentService = {
      getActivityStudents: jasmine.createSpy('getActivityStudents').and.returnValue(of([]))
    };

    mockGradeService = jasmine.createSpyObj('GradeService', ['giveGrades']);
    (mockGradeService.giveGrades as jasmine.Spy).and.returnValue(of(new HttpResponse<Grade[]>({ body: [] })).pipe(
      catchError((error: any) => {
        return throwError({ status: 500, message: 'Internal server error' });
      })
    ));

    await TestBed.configureTestingModule({
      declarations: [GradingDialogComponent],
      imports: [
        HttpClientTestingModule,
        BrowserAnimationsModule,
        MatInputModule,
        MatDialogModule,
        MatTableModule,
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: MatDialogRef, useValue: mockDialogRef },
        { provide: StudentService, useValue: mockStudentService },
        { provide: GradeService, useValue: mockGradeService } 
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GradingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load students when task is present', () => {
    // Set the task to trigger the ngOnInit call
    component.task = { id: 1, deadline: '2023-07-31', description: 'Sample task', status_id: 1, activity_id: 1 };
    component.ngOnInit();
    expect(mockStudentService.getActivityStudents).toHaveBeenCalledWith(1);
  });

  it('should save grades successfully', () => {
    component.students = [
      { id: 1, name: 'Student 1', email: 'student1@example.com', leader: true, team_id: 1 },
      { id: 2, name: 'Student 2', email: 'student2@example.com', leader: false, team_id: 2 }
    ];
    component.task = { id: 1, deadline: '2023-07-31', description: 'Sample task', status_id: 1, activity_id: 1 };
    component.saveGrades();
    expect(mockGradeService.giveGrades).toHaveBeenCalledWith(1, 1, 1, jasmine.any(Array));
    expect(mockDialogRef.close).toHaveBeenCalledWith(true);
  });

  it('should handle errors when saving grades', () => {
    const error = { status: 500, message: 'Internal server error' };
    (mockGradeService.giveGrades as jasmine.Spy).and.returnValue(throwError(error));
    component.students = [{ id: 1, name: 'Student 1', email: 'student1@example.com', leader: true, team_id: 1 }];
    component.task = { id: 1, deadline: '2023-07-31', description: 'Sample task', status_id: 1, activity_id: 1 };
    component.saveGrades();
    expect(mockGradeService.giveGrades).toHaveBeenCalledWith(1, 1, 1, jasmine.any(Array));
    expect(mockDialogRef.close).not.toHaveBeenCalled();
  });
});

