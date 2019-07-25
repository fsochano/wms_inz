import { TestBed } from '@angular/core/testing';

import { OrderLinesService } from './order-lines.service';

describe('OrderLinesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OrderLinesService = TestBed.get(OrderLinesService);
    expect(service).toBeTruthy();
  });
});
