import { WarehouseLocation, LocationType } from './store/location.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Container } from '../container/store/containers.model';


@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private readonly http: HttpClient) {
  }

  loadLocations(): Observable<WarehouseLocation[]> {
    return this.http.get<WarehouseLocation[]>('/api/location');
  }

  getLocationContainers(locationId: number): Observable<Container[]> {
    return this.http.get<Container[]>(`/api/location/${locationId}/containers`);
  }

  createLocation(locationParameters: { name: string; locationType: LocationType, capacity: number }): Observable<WarehouseLocation> {
    return this.http.post<WarehouseLocation>(`/api/location`, locationParameters);
  }
}
