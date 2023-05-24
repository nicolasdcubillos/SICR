import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarCategoriaComponent } from './gestionar-categoria.component';

describe('GestionarCategoriaComponent', () => {
  let component: GestionarCategoriaComponent;
  let fixture: ComponentFixture<GestionarCategoriaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionarCategoriaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarCategoriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
