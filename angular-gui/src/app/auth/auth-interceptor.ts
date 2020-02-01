import { AuthSelectors } from './auth.selectror';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { map, switchMap, first } from 'rxjs/operators';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private readonly store: Store<{}>,
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.store.select(AuthSelectors.token).pipe(
      first(),
      map(token => token ? req.clone({ headers: req.headers.set('Authorization', token) }) : req),
      switchMap(r => next.handle(r)),
    );
  }
}
