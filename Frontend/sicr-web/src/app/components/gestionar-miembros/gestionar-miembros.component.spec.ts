import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarMiembrosComponent } from './gestionar-miembros.component';

describe('GestionarMiembrosComponent', () => {
  let component: GestionarMiembrosComponent;
  let fixture: ComponentFixture<GestionarMiembrosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionarMiembrosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarMiembrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
