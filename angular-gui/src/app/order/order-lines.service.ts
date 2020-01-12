import { BASE_API_URL } from './../http-util';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderLine } from './store/orders.model';
import { HttpClient } from '@angular/common/http';

const BASE_URL = `${BASE_API_URL}/orders`;

@Injectable({
  providedIn: 'root'
})
export class OrderLinesService {

  constructor(private readonly http: HttpClient) { }

  loadOrderLines(orderId: number): Observable<OrderLine[]> {
    return this.http.get<OrderLine[]>(`${BASE_URL}/${orderId}/order-lines`);
  }

  createOrderLine(orderId: number, params: { qty: number, skuId: number }): Observable<OrderLine> {
    return this.http.post<OrderLine>(`${BASE_URL}/${orderId}/order-lines`, params);
  }

  removeOrderLine(orderId: number, orderLineId: number) {
    return this.http.delete<void>(`${BASE_URL}/${orderId}/order-lines/${orderLineId}`);
  }

}
