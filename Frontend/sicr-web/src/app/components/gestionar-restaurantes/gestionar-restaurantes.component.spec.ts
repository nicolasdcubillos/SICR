import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarRestaurantesComponent } from './gestionar-restaurantes.component';

describe('GestionarRestaurantesComponent', () => {
  let component: GestionarRestaurantesComponent;
  let fixture: ComponentFixture<GestionarRestaurantesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionarRestaurantesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarRestaurantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
