import { Injectable } from '@angular/core';
import { Order, OrderStatus } from './store/orders.model';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(private readonly http: HttpClient) { }

  loadOrders(): Observable<Order[]> {
    return this.http.get<Order[]>('/api/orders');
  }

  loadOrder(id: number): Observable<Order> {
    return this.http.get<Order>(`/api/orders/${id}`);
  }

  createOrder(name: string): Observable<Order> {
    return this.http.post<Order>('/api/orders', { name });
  }

  removeOrder(id: number): Observable<any> {
    return this.http.delete<void>(`/api/orders/${id}`);
  }

  updateOrderStatus(id: number, status: OrderStatus): Observable<Order> {
    return this.http.post<Order>(`/api/orders/${id}`, { status });
  }
}
