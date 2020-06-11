import { ColumnSchema } from 'src/app/shared/table/column-schema.model';
import { Component, OnInit } from '@angular/core';
import { of } from 'rxjs';
import { Shipment } from './shipment.model';
import { Store } from '@ngrx/store';
import { ShipmentSelectors } from './store/shipment.selector';
import { ShipmentActions } from './store/shipment.actions';
import { ShipmentService } from './shipment.service';

@Component({
  selector: 'app-shipment',
  templateUrl: './shipment.component.html',
  styleUrls: ['./shipment.component.scss']
})
export class ShipmentComponent implements OnInit {

  readonly columnSchema: ColumnSchema<Shipment>[] = [
    { header: 'shipment Id', key: 'id' },
    { header: 'shipment Name', key: 'orderName' },
    { header: 'shipment Status', key: 'status' },
  ];
  readonly displayedColumns = [...this.columnSchema.map(s => s.key), 'bt-actions'];
 shipments$ = this.store.select(ShipmentSelectors.selectAllShipment);

  constructor(
    private readonly store: Store<{}>,
    private shipmentService: ShipmentService,
  ) { }

  ngOnInit() {
    this.store.dispatch(ShipmentActions.shipmentRequested());
  }

  ship(shipment: Shipment) {
    this.shipmentService.ship(shipment).subscribe(shipment => {
      this.store.dispatch(ShipmentActions.shipmentUpdated({ shipment }));
    });
  }

}
