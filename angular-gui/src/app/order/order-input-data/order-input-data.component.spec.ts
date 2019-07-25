import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderInputDataComponent } from './order-input-data.component';

describe('OrderInputDataComponent', () => {
  let component: OrderInputDataComponent;
  let fixture: ComponentFixture<OrderInputDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderInputDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderInputDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
