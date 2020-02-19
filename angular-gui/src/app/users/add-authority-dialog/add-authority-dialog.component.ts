import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-authority-dialog',
  templateUrl: './add-authority-dialog.component.html',
  styleUrls: ['./add-authority-dialog.component.scss']
})
export class AddAuthorityDialogComponent {

  form: FormGroup;
  error?: string;
  authorities = ['ORDERING', 'PICKING', 'SHIPPING', 'SETTINGS'];

  constructor(
    public dialogRef: MatDialogRef<AddAuthorityDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    fb: FormBuilder,
    ) {
      this.form = fb.group({
        authority: fb.control(null, [Validators.required]),
      });
    }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
