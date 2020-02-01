import { ColumnSchema } from '../shared/table/column-schema.model';
import { WarehouseLocation } from './store/location.model';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { LocationsActions } from './store/location.actions';
import { LocationsSelectors } from './store/location.selectors';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.scss']
})
export class LocationComponent implements OnInit {

  locations$: Observable<WarehouseLocation[]> = this.store.select(LocationsSelectors.selectAllLocations);

  columnSchema: ColumnSchema<WarehouseLocation>[] = [
    { header: 'Location Name', key: 'name' },
    { header: 'Location type', key: 'locationType' },
    { header: 'Location capacity', key: 'capacity' },
    { header: 'Location used capacity', key: 'usedCapacity' },
    { header: 'Location free capacity', key: 'freeCapacity' },
  ];

  displayedColumns: string[] = [...this.columnSchema.map(c => c.key), 'bt-details'];

  constructor(
    private store: Store<{}>,
  ) {
    this.store.dispatch(LocationsActions.locationsRequested());
  }

  ngOnInit() {
  }

}
