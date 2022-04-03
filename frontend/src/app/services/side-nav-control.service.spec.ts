import { TestBed } from '@angular/core/testing';

import { SideNavControlService } from './side-nav-control.service';

describe('SideNavControlService', () => {
  let service: SideNavControlService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SideNavControlService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
