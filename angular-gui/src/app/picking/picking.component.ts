import { ColumnSchema } from '../shared/table/column-schema.model';
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
    { header: 'Id', key: 'id' },
    { header:'Order Id', key:'orderId'},
    { header: 'Status', key: 'status' },
    { header: 'Last Change By', key: 'lastModifiedBy' },
    { header: 'Last Change Date', key: 'lastModifiedDate' },
  ];
  displayedColumns = [...this.columnSchema.map(s => s.key), 'bt-details'];

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
