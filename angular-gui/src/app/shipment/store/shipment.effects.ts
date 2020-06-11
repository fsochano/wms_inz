import { ShipmentActions } from './shipment.actions';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { switchMap, map } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { ShipmentService } from '../shipment.service';

@Injectable({
  providedIn: 'root',
})
export class ShipmentEffects {

    loadShipments$ = createEffect(() =>
        this.actions$.pipe(
            ofType(ShipmentActions.shipmentRequested),
            switchMap(() => this.shipmentService.loadShipments()),
            map(shipments => ShipmentActions.shipmentLoaded({ shipments })),
        ));

    constructor(
        private actions$: Actions,
        private shipmentService: ShipmentService,
    ) { }
}
