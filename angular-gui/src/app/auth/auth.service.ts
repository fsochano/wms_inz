import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { User } from './auth.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private readonly http: HttpClient) { }

  login(email: string, password: string): Observable<User> {
    return this.http.post<User>('/api/login', { username: email, password });
  }

  logout(): Observable<unknown> {
    return this.http.get<unknown>('/api/logout');
  }
}
