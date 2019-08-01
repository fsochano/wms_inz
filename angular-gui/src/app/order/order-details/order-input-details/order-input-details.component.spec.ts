import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderInputDetailsComponent } from './order-input-details.component';

describe('OrderInputDetailsComponent', () => {
  let component: OrderInputDetailsComponent;
  let fixture: ComponentFixture<OrderInputDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderInputDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderInputDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
