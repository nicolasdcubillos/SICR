import { TestBed } from '@angular/core/testing';

import { SedeRestauranteService } from './sede-restaurante.service';

describe('SedeRestauranteService', () => {
  let service: SedeRestauranteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SedeRestauranteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
