import { ColumnSchema } from '../../shared/table/column-schema.model';
import { PickTasksSelectors } from './store/pick-tasks.selector';
import { PickTasksActions } from './store/pick-tasks.actions';
import { PickTasksService } from './pick-tasks.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { PickTask } from './pick-tasks.model';

@Component({
  selector: 'app-pick-task',
  templateUrl: './pick-task.component.html',
  styleUrls: ['./pick-task.component.scss']
})
export class PickTaskComponent implements OnInit {

  columnSchema: ColumnSchema<PickTask>[] = [
    { key: 'id', header: 'Id' },
    { key: 'fromLocationName', header: 'Source location' },
    { key: 'fromContainerId', header: 'Source container' },
    { key: 'qty', header: 'Quantity to pick' },
    { key: 'skuName', header: 'Sku name' },
    { key: 'toLocationName', header: 'Destination location' },
    { key: 'toContainerId', header: 'Destination container' },
    { key: 'lastModifiedBy', header: 'Last Change By' },
    { key: 'lastModifiedDate', header: 'Last Change Date' },
  ];
  displayedColumns = [...this.columnSchema.map(s => s.key), 'bt-actions'];

  moveTasks$ = this.store.select(PickTasksSelectors.selectAllPickTasks);

  constructor(
    private readonly route: ActivatedRoute,
    private readonly store: Store<{}>,
    private readonly pickTasksService: PickTasksService,
  ) { }

  ngOnInit() {
    this.store.dispatch(PickTasksActions.pickTaksRequested({ pickListId: this.pickListId }));
  }

  isDisabled(elem: PickTask) {
    switch (elem.status) {
      case 'READY':
      case 'PICKED':
        return false;
      default:
        return true;
    }
  }

  getButtonName(elem: PickTask) {
    switch (elem.status) {
      case 'READY':
        return 'Pick';
      case 'PICKED':
        return 'Complete';
      default:
        return elem.status.charAt(0).toUpperCase() + elem.status.slice(1).toLowerCase();
    }
  }

  changeState(elem: PickTask) {
    if (elem.status === 'READY') {
      this.pickTasksService.pickPickTask(elem.id).subscribe(
        pickTask => this.store.dispatch(PickTasksActions.pickTaskUpdated({ pickTask })),
      )
    } else if(elem.status === 'PICKED') {
      this.pickTasksService.completePickTask(elem.id).subscribe(
        pickTask => this.store.dispatch(PickTasksActions.pickTaskUpdated({ pickTask })),
      )
    }
  }

  get pickListId() {
    return this.route.snapshot.params.pickListId;
  }

}
