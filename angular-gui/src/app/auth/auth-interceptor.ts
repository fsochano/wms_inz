import { AuthSelectors } from './auth.selectror';
import { HttpClient, HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppState } from '../app.reducer';
import { Store } from '@ngrx/store';
import { map, switchMap, first } from 'rxjs/operators';

export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private readonly store: Store<AppState>,
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.store.select(AuthSelectors.token).pipe(
      first(),
      map(token => token ? req.clone({ headers: req.headers.set('Authorization', token) }) : req),
      switchMap(r => next.handle(r)),
    );
  }
}
