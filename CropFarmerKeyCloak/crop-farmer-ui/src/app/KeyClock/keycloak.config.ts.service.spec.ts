import { TestBed } from '@angular/core/testing';

import { KeycloakConfigTsService } from './keycloak.config.ts.service';

describe('KeycloakConfigTsService', () => {
  let service: KeycloakConfigTsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KeycloakConfigTsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
