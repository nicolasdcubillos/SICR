import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarMenuComponent } from './gestionar-menu.component';

describe('GestionarMenuComponent', () => {
  let component: GestionarMenuComponent;
  let fixture: ComponentFixture<GestionarMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionarMenuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
