import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarInventarioComponent } from './gestionar-inventario.component';

describe('GestionarInventarioComponent', () => {
  let component: GestionarInventarioComponent;
  let fixture: ComponentFixture<GestionarInventarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionarInventarioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarInventarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
