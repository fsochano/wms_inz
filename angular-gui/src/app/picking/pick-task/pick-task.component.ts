import { ColumnSchema } from './../../shared/column-schema.model';
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
    { param: 'id', name: 'Id' },
    { param: 'fromLocationName', name: 'Source location' },
    { param: 'fromContainerId', name: 'Source container' },
    { param: 'qty', name: 'Quantity to pick' },
    { param: 'toLocationName', name: 'Destination location' },
    { param: 'toContainerId', name: 'Destination container' },
  ];
  displayedColumns = [...this.columnSchema.map(s => s.param), 'bt-actions'];

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
