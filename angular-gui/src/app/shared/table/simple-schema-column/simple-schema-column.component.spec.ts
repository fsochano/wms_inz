import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SimpleSchemaColumnComponent } from './simple-schema-column.component';

describe('SimpleSchemaColumnComponent', () => {
  let component: SimpleSchemaColumnComponent;
  let fixture: ComponentFixture<SimpleSchemaColumnComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SimpleSchemaColumnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SimpleSchemaColumnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
