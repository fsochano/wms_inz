import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-change-password-dialog',
  templateUrl: './change-password-dialog.component.html',
  styleUrls: ['./change-password-dialog.component.scss']
})
export class ChangePasswordDialogComponent {

  form: FormGroup;
  error?: string;

  constructor(
    public dialogRef: MatDialogRef<ChangePasswordDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    fb: FormBuilder,
  ) {
    const password = fb.control(null, [Validators.required, Validators.minLength(6), Validators.maxLength(50)]);
    this.form = fb.group({
      password,
      repeatPassword: fb.control(null, [
        Validators.required,
        (control) => control.value === password.value ? null : { passwordNotEqual: 'Password are not equal' },
      ]),
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  changePassword() {
    this.dialogRef.close(this.form.value.password);
  }

}
