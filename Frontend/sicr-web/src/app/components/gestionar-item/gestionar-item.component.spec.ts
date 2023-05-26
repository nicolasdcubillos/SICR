import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarItemComponent } from './gestionar-item.component';

describe('GestionarItemComponent', () => {
  let component: GestionarItemComponent;
  let fixture: ComponentFixture<GestionarItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionarItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
