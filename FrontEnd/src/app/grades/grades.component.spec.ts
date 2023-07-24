import { ComponentFixture, TestBed } from '@angular/core/testing';
import { GradesComponent } from './grades.component';
import { Location } from '@angular/common';

describe('GradesComponent', () => {
  let component: GradesComponent;
  let fixture: ComponentFixture<GradesComponent>;
  let locationSpy: jasmine.SpyObj<Location>;

  beforeEach(() => {
    const locationMock = jasmine.createSpyObj('Location', ['back']);

    TestBed.configureTestingModule({
      declarations: [GradesComponent],
      providers: [{ provide: Location, useValue: locationMock }]
    }).compileComponents();

    fixture = TestBed.createComponent(GradesComponent);
    component = fixture.componentInstance;
    locationSpy = TestBed.inject(Location) as jasmine.SpyObj<Location>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call location.back() when goBack is called', () => {
    component.goBack();

    expect(locationSpy.back).toHaveBeenCalled();
  });
});
