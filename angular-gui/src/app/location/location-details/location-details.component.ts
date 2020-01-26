import { LocationsActions } from './../store/location.actions';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { ContainersSelectors } from '../../container/store/containers.selectors';
import { Container } from '../../container/store/containers.model';
import { ColumnSchema } from 'src/app/shared/column-schema.model';

@Component({
  selector: 'app-location-details',
  templateUrl: './location-details.component.html',
  styleUrls: ['./location-details.component.scss']
})
export class LocationDetailsComponent implements OnInit {
  columnSchema: ColumnSchema<Container>[]  = [
    { param: 'id', name: 'Id'},
    { param: 'containerSize', name: 'Container size' },
    { param: 'skuQty', name: 'Sku quantity' },
    { param: 'skuCapacity', name: 'Sku capacity' },
  ];
  displayedColumns = ['id', 'containerSize', 'skuName', 'skuQty', 'skuCapacity'];
  containers$: Observable<Container[]> = this.store.select(ContainersSelectors.selectAllContainers);

  constructor(
    private readonly route: ActivatedRoute,
    private readonly store: Store<{}>,
  ) { }

  ngOnInit() {
    this.store.dispatch(LocationsActions.locationsContainersRequested({ locationId: this.locationId }));
  }

  get locationId() {
    return this.route.snapshot.params.locationId;
  }

}
