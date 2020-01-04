import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { OrderLine } from './store/orders.model';
import { Dictionary } from '@ngrx/entity';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OrderLinesService {

  constructor(private readonly http: HttpClient) { }

  loadOrderLines(orderId: number): Observable<OrderLine[]> {
    return this.http.get<OrderLine[]>(`/api/orders/${orderId}/order-lines`);
  }

  createOrderLine(orderId: number, params: { qty: number, item: string }): Observable<OrderLine> {
    return this.http.post<OrderLine>(`/api/orders/${orderId}/order-lines`, params);
  }

  removeOrderLine(orderId: number, orderLineId: number) {
    return this.http.delete<void>(`/api/orders/${orderId}/order-lines/${orderLineId}`);
  }
}
