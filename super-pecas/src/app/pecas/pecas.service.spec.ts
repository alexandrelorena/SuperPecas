/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PecasService } from './pecas.service';

describe('Service: Pecas', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PecasService]
    });
  });

  it('should ...', inject([PecasService], (service: PecasService) => {
    expect(service).toBeTruthy();
  }));
});
