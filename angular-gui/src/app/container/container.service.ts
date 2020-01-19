import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Container } from './store/containers.model';

interface ContainerParams {
  locationId: number;
  containerSize: number;
  skuId: number;
  skuQty: number;
  skuCapacity: number;
}

@Injectable({
  providedIn: 'root'
})
export class ContainerService {

  constructor(private readonly http: HttpClient) {
  }

  createContainer(containerParams: ContainerParams): Observable<Container> {
    return this.http.post<Container>('/api/container', containerParams);
  }
}
