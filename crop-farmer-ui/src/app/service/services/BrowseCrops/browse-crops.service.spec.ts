import { TestBed } from '@angular/core/testing';

import { BrowseCropsService } from './browse-crops.service';

describe('BrowseCropsService', () => {
  let service: BrowseCropsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BrowseCropsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
