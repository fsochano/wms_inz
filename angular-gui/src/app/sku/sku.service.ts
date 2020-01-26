import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sku } from './store/sku.model';
import { HttpClient } from '@angular/common/http';
import { BASE_API_URL } from '../http-util';

const BASE_PATH = `${BASE_API_URL}/sku`;

@Injectable({
  providedIn: 'root'
})
export class SkuService {

  constructor(private readonly http: HttpClient) { }

  loadSkus(): Observable<Sku[]> {
    return this.http.get<Sku[]>(BASE_PATH);
  }

  createSku(name: string, description: string): Observable<Sku> {
    return this.http.post<Sku>(BASE_PATH, { name, description });
  }

  removeSku({ id }: Sku): Observable<any>{
    return this.http.delete<void>(`${BASE_PATH}/${id}`);
  }
}
