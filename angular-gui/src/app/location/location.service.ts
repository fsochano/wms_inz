import { WarehouseLocation } from './store/location.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private readonly http: HttpClient) {
  }

  loadLocations(): Observable<WarehouseLocation[]> {
    return this.http.get<WarehouseLocation[]>('/api/location');
  }
}
