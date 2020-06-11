import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Shipment } from './shipment.model';

@Injectable({
  providedIn: 'root'
})
export class ShipmentService {

  constructor(private readonly http: HttpClient) { }

  loadShipments(): Observable<Shipment[]> {
    return this.http.get<Shipment[]>('/api/shipments');
  }

  ship({ id }: Shipment) {
    return this.http.post<Shipment>(`/api/shipments/${id}/ship`, { status: 'SHIPPED' });
  }

}
