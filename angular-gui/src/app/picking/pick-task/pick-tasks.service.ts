import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PickTask } from './pick-tasks.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PickTasksService {

  constructor(private readonly http: HttpClient) { }

  loadPickTasks(pickListId: number): Observable<PickTask[]> {
    return this.http.get<PickTask[]>(`/api/pick-lists/${pickListId}/pick-tasks`);
    // return of([
    //   { id: 1337, fromLocation: 'ASD', fromContainer: 'DSA', qty: 5, toLocation: 'QWE', toContainer: 'EWQ', status: 'READY' },
    //   { id: 1338, fromLocation: 'ASD', fromContainer: 'DSA', qty: 5, toLocation: 'QWE', toContainer: 'EWQ', status: 'PICKED' },
    //   { id: 1339, fromLocation: 'ASD', fromContainer: 'DSA', qty: 5, toLocation: 'QWE', toContainer: 'EWQ', status: 'COMPLETED' },
    //   { id: 1340, fromLocation: 'ASD', fromContainer: 'DSA', qty: 5, toLocation: 'QWE', toContainer: 'EWQ', status: 'SHIPPED' },
    // ]);
  }

  pickPickTask(id: number): Observable<PickTask> {
    return this.http.post<PickTask>(`/api/pick-tasks/${id}/status`, { status: 'PICKED' });
  }

  completePickTask(id: number): Observable<PickTask> {
    return this.http.post<PickTask>(`/api/pick-tasks/${id}/status`, { status: 'COMPLETED' });
  }
}
