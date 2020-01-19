import { Component, OnInit } from '@angular/core';
import { of } from 'rxjs';

@Component({
  selector: 'app-picking',
  templateUrl: './picking.component.html',
  styleUrls: ['./picking.component.scss']
})
export class PickingComponent implements OnInit {

  columnSchema = [
    { name: 'Id', param: 'id' },
    { name: 'Status', param: 'status' },
  ];
  displayedColumns = [...this.columnSchema.map(s => s.param), 'bt-details'];

  pickTasks$ = of([
    { id: 123, status: 'RELEASED' },
    { id: 124, status: 'IN_PROGRESS' },
    { id: 125, status: 'COMPLETED' },
    { id: 126, status: 'SHIPPED' },
  ]);

  constructor() { }

  ngOnInit() {
  }

  isDisabled(elem: { status: string }) {
    return elem.status === 'COMPLETED' || elem.status === 'SHIPPED';
  }

}
