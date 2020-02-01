import { User, Authority } from './../auth.model';
import { AuthActions } from './../auth.action';
import { AuthService } from './../auth.service';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { getRedirectPath } from '../../app-routing-helper';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  form: FormGroup;
  error?: string;

  constructor(
    fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private store: Store<{}>) {
    this.form = fb.group({
      email: ['admin', [Validators.required]],
      password: ['password', [Validators.required]]
    });
  }

  login() {
    this.error = undefined;
    const val = this.form.value;
    this.auth.login(val.email, val.password)
      .subscribe(
        user => {
          this.store.dispatch(AuthActions.login({ user }));
          this.navigate(user);
        },
        () => this.error = 'Login failed',
      );
  }

  private navigate(user: User) {
    const path = getRedirectPath(user.authorities);
    if (path) {
      void this.router.navigateByUrl(path);
    } else {
      this.store.dispatch(AuthActions.logout());
      this.error = 'No authorities assigned';
    }
  }
}
