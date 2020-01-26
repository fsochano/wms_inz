import { ColumnSchema } from './../shared/column-schema.model';
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
    { name: 'Location Name', param: 'name' },
    { name: 'Location type', param: 'locationType' },
    { name: 'Location capacity', param: 'capacity' },
    { name: 'Location used capacity', param: 'usedCapacity' },
    { name: 'Location free capacity', param: 'freeCapacity' },
  ];
  
  displayedColumns: string[] = [...this.columnSchema.map(c => c.param), 'bt-details'];

  constructor(
    private store: Store<{}>,
  ) {
    this.store.dispatch(LocationsActions.locationsRequested());
  }

  ngOnInit() {
  }

}
