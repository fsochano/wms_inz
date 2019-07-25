import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { OrderLine } from './store/orders.model';

@Injectable({
  providedIn: 'root'
})
export class OrderLinesService {

  constructor() { }

  loadOrderLines(id: string): Observable<OrderLine[]> {
    return of([
      { id: '1', qty: 5, item: 'doge' },
    ]);
  }
}
