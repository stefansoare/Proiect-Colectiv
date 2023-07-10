import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MentorAccountComponent } from './mentor-account.component';

describe('MentorAccountComponent', () => {
  let component: MentorAccountComponent;
  let fixture: ComponentFixture<MentorAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MentorAccountComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MentorAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

 //it('should create', () => {
  //  expect(component).toBeTruthy();
  //});
});
