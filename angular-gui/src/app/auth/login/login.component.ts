import { AuthActions } from './../auth.action';
import { AuthService } from './../auth.service';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';

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
    private store: Store<{}>) {
    this.form = fb.group({
      email: ['admin', [Validators.required]],
      password: ['password', [Validators.required]]
    });
  }

  login() {
    const val = this.form.value;
    this.auth.login(val.email, val.password)
    .subscribe(
        user => {
          this.store.dispatch(AuthActions.login({ user }));
          this.router.navigateByUrl('/order');
        },
        () => alert('Login Failed'),
      );
  }
}
