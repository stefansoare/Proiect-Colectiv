import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing'; 

import { RegisterActivityComponent } from './register-activity.component';
import { MatDialogModule } from '@angular/material/dialog';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('RegisterActivityComponent', () => {
  let component: RegisterActivityComponent;
  let fixture: ComponentFixture<RegisterActivityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterActivityComponent],
      imports: [HttpClientTestingModule,  MatDialogModule],
      schemas: [
        NO_ERRORS_SCHEMA
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterActivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
