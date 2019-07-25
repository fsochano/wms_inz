import { AuthActions } from './../auth.action';
import { AppState } from './../../app.reducer';
import { AuthService } from './../auth.service';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { switchMap, tap } from 'rxjs/operators';
import { from, noop } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  form: FormGroup;

  constructor(
    fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private store: Store<AppState>) {
    this.form = fb.group({
      email: ['test@wms.com', [Validators.required]],
      password: ['test', [Validators.required]]
    });
  }

  login() {
    const val = this.form.value;
    this.auth.login(val.email, val.password)
      .pipe(
        tap(user => {
          this.store.dispatch(AuthActions.login({ user }));
          this.router.navigateByUrl('/order');
        })
      ).subscribe(
        noop,
        () => alert('Login Failed'),
      );
  }
}
