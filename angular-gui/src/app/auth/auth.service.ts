import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { User } from './auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor() { }

  login(email: string, password: string): Observable<User> {
    return of({
      id: 1,
      name: email,
    });
  }
}
