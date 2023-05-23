import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarPromocionesComponent } from './gestionar-promociones.component';

describe('GestionarPromocionesComponent', () => {
  let component: GestionarPromocionesComponent;
  let fixture: ComponentFixture<GestionarPromocionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionarPromocionesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarPromocionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
