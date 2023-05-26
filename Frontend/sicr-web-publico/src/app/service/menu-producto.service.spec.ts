import { TestBed } from '@angular/core/testing';

import { MenuProductoService } from './menu-producto.service';

describe('MenuProductoService', () => {
  let service: MenuProductoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MenuProductoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
