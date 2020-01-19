import { LocationsActions } from './../store/location.actions';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { LocationService } from '../location.service';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/app.reducer';
import { LocationType } from '../store/location.model';

@Component({
  selector: 'app-location-input',
  templateUrl: './location-input.component.html',
  styleUrls: ['./location-input.component.scss']
})
export class LocationInputComponent implements OnInit {

  form: FormGroup;
  error?: string;
  locationTypes: LocationType[] = [LocationType.INBOUND, LocationType.SHIPDOCK, LocationType.STORAGE];


  constructor(
    fb: FormBuilder,
    private service: LocationService,
    private store: Store<AppState>
  ) {
    this.form = fb.group({
      name: [null, [Validators.required,]],
      locationType: [null, [Validators.required]],
      capacity: [null, [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit() {
  }

  createLocation() {
    this.error = undefined;
    this.service.createLocation(this.form.value)
      .subscribe(
        location => this.store.dispatch(LocationsActions.locationCreated({ location })),
        error => this.error = error.error.message
      );
  }

}
