import { TestBed } from '@angular/core/testing';

import { CropServiceTsService } from './crop.service';

describe('CropServiceTsService', () => {
  let service: CropServiceTsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CropServiceTsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
