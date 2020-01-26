import { ColumnSchema } from './../shared/column-schema.model';
import { PickListsSelectors } from './store/pick-lists.selector';
import { PickListsActions } from './store/pick-lists.actions';
import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { PickList } from './pick-lists.model';

@Component({
  selector: 'app-picking',
  templateUrl: './picking.component.html',
  styleUrls: ['./picking.component.scss']
})
export class PickingComponent implements OnInit {

  columnSchema: ColumnSchema<PickList>[] = [
    { name: 'Id', param: 'id' },
    { name: 'Status', param: 'status' },
  ];
  displayedColumns = [...this.columnSchema.map(s => s.param), 'bt-details'];

  pickTasks$ = this.store.select(PickListsSelectors.selectAllPickLists);

  constructor(
    private readonly store: Store<{}>,
  ) { }

  ngOnInit() {
    this.store.dispatch(PickListsActions.pickListsRequested());
  }

  // isDisabled(elem: PickList) {
  //   return elem.status === 'COMPLETED' || elem.status === 'SHIPPED';
  // }

}
