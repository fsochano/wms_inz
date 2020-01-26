import { Injectable } from '@angular/core';
import { of, Observable } from 'rxjs';
import { PickList } from './pick-lists.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PickListService {

  constructor(private readonly http: HttpClient) { }

  loadPicking(): Observable<PickList[]> {
    return this.http.get<PickList[]>('/api/pick-lists');
    // return of([
    //   { id: 123, status: 'RELEASED' },
    //   { id: 124, status: 'IN_PROGRESS' },
    //   { id: 125, status: 'COMPLETED' },
    //   { id: 126, status: 'SHIPPED' },
    // ]);
  }

}
