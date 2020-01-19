import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

@Component({
  selector: 'app-pick-task',
  templateUrl: './pick-task.component.html',
  styleUrls: ['./pick-task.component.scss']
})
export class PickTaskComponent implements OnInit {

  columnSchema = [
    { param: 'id', name: 'Id' },
    { param: 'fromLocation', name: 'Source location' },
    { param: 'fromContainer', name: 'Source container' },
    { param: 'qty', name: 'Quantity to pick' },
    { param: 'toLocation', name: 'Destination location' },
    { param: 'toContainer', name: 'Destination container' },
  ];
  displayedColumns = [...this.columnSchema.map(s => s.param), 'bt-actions'];

  moveTasks$ = of([
    { id: 1337, fromLocation: 'ASD', fromContainer: 'DSA', qty: 5, toLocation: 'QWE', toContainer: 'EWQ', status: 'READY' },
    { id: 1338, fromLocation: 'ASD', fromContainer: 'DSA', qty: 5, toLocation: 'QWE', toContainer: 'EWQ', status: 'PICKED' },
    { id: 1339, fromLocation: 'ASD', fromContainer: 'DSA', qty: 5, toLocation: 'QWE', toContainer: 'EWQ', status: 'COMPLETED' },
    { id: 1340, fromLocation: 'ASD', fromContainer: 'DSA', qty: 5, toLocation: 'QWE', toContainer: 'EWQ', status: 'SHIPPED' },
  ]);

  constructor(
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
  }

  isDisabled(elem: { status: string }) {
    switch (elem.status) {
      case 'READY':
      case 'PICKED':
        return false;
      default:
        return true;
    }
  }

  getButtonName(elem: { status: string }) {
    switch (elem.status) {
      case 'READY':
        return 'Pick';
      case 'PICKED':
        return 'Complete';
      default:
        return elem.status.charAt(0).toUpperCase() + elem.status.slice(1).toLowerCase();
    }
  }

  get pickTaskId() {
    return this.route.snapshot.params.pickTaskId;
  }
}
