import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AppUserParams } from '../app-user.model';
import { UsersService } from '../users.service';
import { Store } from '@ngrx/store';
import { AppUsersActions } from '../store/users.actions';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-user-creation-form',
  templateUrl: './user-creation-form.component.html',
  styleUrls: ['./user-creation-form.component.scss']
})
export class UserCreationFormComponent implements OnInit {

  form: FormGroup;
  error?: string;

  constructor(
    fb: FormBuilder,
    private readonly service: UsersService,
    private readonly store: Store<{}>,
  ) {
    const password = fb.control(null, [Validators.required, Validators.minLength(6), Validators.maxLength(50)]);
    this.form = fb.group({
      username: fb.control(null, [Validators.required, Validators.email]),
      password,
      repeatPassword: fb.control(null, [
        Validators.required,
        (control) => control.value === password.value ? null : { passwordNotEqual: 'Password are not equal' },
      ]),
    });
  }

  ngOnInit() {
  }

  createUser() {
    this.form.disable();
    this.error = undefined;
    this.service.createUser(this.form.value)
    .pipe(
      finalize(() => this.form.enable()),
    )
    .subscribe(
      user => this.store.dispatch(AppUsersActions.userCreated({ user })),
      error => this.error = 'Error: ' + error.error.message,
    );
  }

}
