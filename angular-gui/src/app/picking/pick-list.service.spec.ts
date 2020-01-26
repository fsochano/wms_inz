import { TestBed } from '@angular/core/testing';
import { PickListService } from './pick-list.service';

describe('PickingListService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PickListService = TestBed.get(PickListService);
    expect(service).toBeTruthy();
  });
});
