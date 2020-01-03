import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderLineDetailsComponent } from './order-line-details.component';

describe('OrderLineDetailsComponent', () => {
  let component: OrderLineDetailsComponent;
  let fixture: ComponentFixture<OrderLineDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderLineDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderLineDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
