import { TestBed } from '@angular/core/testing';

import { PickTasksService } from './pick-tasks.service';

describe('PickTasksService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PickTasksService = TestBed.get(PickTasksService);
    expect(service).toBeTruthy();
  });
});
