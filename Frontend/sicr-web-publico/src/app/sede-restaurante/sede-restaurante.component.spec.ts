import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SedeRestauranteComponent } from './sede-restaurante.component';

describe('SedeRestauranteComponent', () => {
  let component: SedeRestauranteComponent;
  let fixture: ComponentFixture<SedeRestauranteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SedeRestauranteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SedeRestauranteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
