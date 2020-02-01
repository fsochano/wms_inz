import { ColumnSchema } from 'src/app/shared/table/column-schema.model';
import { Component, OnInit } from '@angular/core';
import { of } from 'rxjs';
import { Shipping } from './shipping.model';

@Component({
  selector: 'app-shipping',
  templateUrl: './shipping.component.html',
  styleUrls: ['./shipping.component.scss']
})
export class ShippingComponent implements OnInit {

  readonly columnSchema: ColumnSchema<Shipping>[] = [
    { header: 'Shipping Id', key: 'id' },
    { header: 'Shipping Name', key: 'name' },
    { header: 'Shipping Status', key: 'status' },
  ];
  readonly displayedColumns = [...this.columnSchema.map(s => s.key), 'bt-actions'];
  shipplings$ = of<Shipping[]>([
    { id: 1, name: 'asd', status: 'Not ready' },
    { id: 2, name: 'dsa', status: 'Ready' },
    { id: 3, name: 'zxc', status: 'Shipped' },
  ]);

  constructor() { }

  ngOnInit() {
  }

  ship(shipping: Shipping) {
    alert(`Shipped ${shipping.name}`);
  }

}
