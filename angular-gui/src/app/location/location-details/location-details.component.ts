import { LocationService } from './../location.service';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { AppState } from '../../app.reducer';
import { Store } from '@ngrx/store';
import { ContainersSelectors } from '../../container/store/containers.selectors';
import { ContainersActions } from '../../container/store/containers.actions';
import { Container } from '../../container/store/containers.model';

@Component({
  selector: 'app-location-details',
  templateUrl: './location-details.component.html',
  styleUrls: ['./location-details.component.scss']
})
export class LocationDetailsComponent implements OnInit {
  columnSchema: { param: keyof Container; name: string }[]  = [
    { param: 'id', name: 'Id'},
    { param: 'containerSize', name: 'Container size' },
    { param: 'skuQty', name: 'Sku quantity' },
    { param: 'skuCapacity', name: 'Sku capacity' },
  ];
  displayedColumns = ['id', 'containerSize', 'skuName', 'skuQty', 'skuCapacity'];
  containers$: Observable<Container[]> = this.store.select(ContainersSelectors.selectAllContainers);

  constructor(
    private readonly service: LocationService,
    private readonly route: ActivatedRoute,
    private readonly store: Store<AppState>,
  ) { }

  ngOnInit() {
    this.service.getLocationContainers(this.locationId).subscribe(
      containers => this.store.dispatch(ContainersActions.containersLoaded({ containers })),
    );
  }

  get locationId() {
    return this.route.snapshot.params.locationId;
  }

}
