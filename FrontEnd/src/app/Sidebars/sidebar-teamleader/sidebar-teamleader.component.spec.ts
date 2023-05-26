import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarTeamleaderComponent } from './sidebar-teamleader.component';

describe('SidebarTeamleaderComponent', () => {
  let component: SidebarTeamleaderComponent;
  let fixture: ComponentFixture<SidebarTeamleaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SidebarTeamleaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidebarTeamleaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
