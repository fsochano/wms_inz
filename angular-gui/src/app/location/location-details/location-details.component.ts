import { LocationsActions } from './../store/location.actions';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { ContainersSelectors } from '../../container/store/containers.selectors';
import { Container } from '../../container/store/containers.model';
import { ColumnSchema } from 'src/app/shared/table/column-schema.model';

@Component({
  selector: 'app-location-details',
  templateUrl: './location-details.component.html',
  styleUrls: ['./location-details.component.scss']
})
export class LocationDetailsComponent implements OnInit {
  columnSchema: ColumnSchema<Container>[]  = [
    { key: 'id', header: 'Id'},
    { key: 'type', header: 'Type'},
    { key: 'containerSize', header: 'Container size' },
    { key: 'sku', header: 'Sku name', param: v => v.sku.name },
    { key: 'skuQty', header: 'Sku quantity' },
    { key: 'skuCapacity', header: 'Sku capacity' },
    { key: 'allocatedQty', header: 'Allocated quantity' },
    { key: 'freeQty', header: 'Free Quantity' },
  ];
  displayedColumns = this.columnSchema.map(s => s.key);
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
