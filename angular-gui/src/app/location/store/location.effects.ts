import { LocationService } from './../location.service';
import { ContainersActions } from './../../container/store/containers.actions';
import { createEffect, ofType, Actions } from '@ngrx/effects';
import { LocationsActions } from './location.actions';
import { map, switchMap, startWith } from 'rxjs/operators';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LocationEffects {

    loadLocations$ = createEffect(() =>
        this.actions$.pipe(
            ofType(LocationsActions.locationsRequested),
            switchMap(() => this.locationService.loadLocations()),
            map(locations => LocationsActions.locationsLoaded({ locations })),
        ));

    loadLocationContainers$ = createEffect(() =>
        this.actions$.pipe(
            ofType(LocationsActions.locationsContainersRequested),
            switchMap(({ locationId }) => this.locationService.getLocationContainers(locationId)
                .pipe(
                    map(containers => ContainersActions.containersLoaded({ containers })),
                    startWith(ContainersActions.containersLoaded({ containers: [] })),
                ),
            )));

    constructor(
        private actions$: Actions,
        private locationService: LocationService,
    ) { }
}
