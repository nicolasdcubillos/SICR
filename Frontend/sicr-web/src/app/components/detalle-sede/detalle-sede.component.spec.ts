import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleSedeComponent } from './detalle-sede.component';

describe('DetalleSedeComponent', () => {
  let component: DetalleSedeComponent;
  let fixture: ComponentFixture<DetalleSedeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetalleSedeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleSedeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
