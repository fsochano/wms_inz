import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkuInputDataComponent } from './sku-input-data.component';

describe('SkuInputDataComponent', () => {
  let component: SkuInputDataComponent;
  let fixture: ComponentFixture<SkuInputDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkuInputDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkuInputDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
