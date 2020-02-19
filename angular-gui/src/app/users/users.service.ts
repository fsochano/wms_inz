import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AppUser, AppUserParams } from './app-user.model';
import { BASE_API_URL } from '../http-util';

const BASE_PATH = `${BASE_API_URL}/users`;

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private readonly http: HttpClient) { }

  loadUsers(): Observable<AppUser[]> {
    return this.http.get<AppUser[]>(BASE_PATH);
  }

  createUser(user: AppUserParams): Observable<AppUser> {
    return this.http.post<AppUser>(BASE_PATH, user);
  }

  removeUser({ username }: AppUser): Observable<void> {
    return this.http.delete<void>(`${BASE_PATH}/${username}`);
  }

  changePassword({ username }: AppUser, password: string): Observable<AppUser> {
    return this.http.post<AppUser>(`${BASE_PATH}/${username}/password`, password);
  }

  addAuthority({ username }: AppUser, authority: string): Observable<AppUser> {
    return this.http.post<AppUser>(`${BASE_PATH}/${username}/authorities/${authority}`, authority);
  }

  removeAuthority({ username }: AppUser, authority: string): Observable<AppUser> {
    return this.http.delete<AppUser>(`${BASE_PATH}/${username}/authorities/${authority}`);
  }
}
