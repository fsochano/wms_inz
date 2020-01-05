import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Sku } from './store/sku.model';

@Injectable({
  providedIn: 'root'
})
export class SkuService {

  skus = [{
    id: 1,
    name: 'asd',
    description: 'dsa',
  },{
    id: 2,
    name: 'dsa',
    description: 'asd',
  }];

  constructor() { }

  loadSkus(): Observable<Sku[]> {
    return of(this.skus);
  }

  
  createSku(name: string, description: string): Observable<Sku> {
    const sku = {
      id: this.skus.length + 1,
      name,
      description,
    };
    this.skus.push(sku);
    return of(sku);
  }
}
