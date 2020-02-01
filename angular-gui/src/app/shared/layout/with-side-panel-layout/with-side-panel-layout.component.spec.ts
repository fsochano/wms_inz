import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WithSidePanelLayoutComponent } from './with-side-panel-layout.component';

describe('WithSidePanelLayoutComponent', () => {
  let component: WithSidePanelLayoutComponent;
  let fixture: ComponentFixture<WithSidePanelLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WithSidePanelLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WithSidePanelLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
