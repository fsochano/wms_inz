import { Injectable } from '@angular/core';
import { Order } from './store/orders.model';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {
  orders = [
    { id: 'order-1', name: 'Order 1' },
    { id: 'order-2', name: 'Order 2' },
    { id: 'order-3', name: 'Order 3' },
  ];

  constructor() { }

  loadOrders(): Observable<Order[]> {
    return of(this.orders);
  }

  loadOrder(id: string): Observable<Order> {
    return of(this.orders.find(order => order.id === id));
  }

  createOrder(id: string, name: string): Observable<Order> {
    const newOrder = {
      id,
      name,
    };
    this.orders.push(newOrder);
    return of(newOrder);
  }

  removeOrder(id: string): Observable<any> {
    return of(id);
  }
}
