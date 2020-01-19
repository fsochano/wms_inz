import { WarehouseLocation } from './store/location.model';
import { LocationService } from './location.service';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { AppState } from '../app.reducer';
import { LocationsActions } from './store/location.actions';
import { LocationsSelectors } from './store/location.selectors';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.scss']
})
export class LocationComponent implements OnInit {

  locations$: Observable<WarehouseLocation[]> = this.store.select(LocationsSelectors.selectAllLocations);

  columnSchema: any[] = [
    { name: 'Location Name', param: 'name' },
    { name: 'Location type', param: 'locationType' },
    { name: 'Location capacity', param: 'capacity' },
    { name: 'Location used capacity', param: 'usedCapacity' },
    { name: 'Location free capacity', param: 'freeCapacity' },
  ];
  //displayedColumns = this.columnSchema.map(c => c.param);
  displayedColumns: string[] = ['name', 'locationType', 'capacity', 'usedCapacity', 'freeCapacity','bt-details'];

  constructor(
    private locationService: LocationService,
    private store: Store<AppState>,
  ) {
    this.locationService.loadLocations()
      .subscribe(locations => this.store.dispatch(LocationsActions.locationsLoaded({ locations })))
  }

  ngOnInit() {
  }

}
