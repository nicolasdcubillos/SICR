import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarSedesComponent } from './gestionar-sedes.component';

describe('GestionarSedesComponent', () => {
  let component: GestionarSedesComponent;
  let fixture: ComponentFixture<GestionarSedesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionarSedesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarSedesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
