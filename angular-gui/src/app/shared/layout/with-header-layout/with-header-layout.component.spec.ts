import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WithHeaderLayoutComponent } from './with-header-layout.component';

describe('WithHeaderLayoutComponent', () => {
  let component: WithHeaderLayoutComponent;
  let fixture: ComponentFixture<WithHeaderLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WithHeaderLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WithHeaderLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
