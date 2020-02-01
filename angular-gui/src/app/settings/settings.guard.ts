import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { AuthorityGuard } from '../auth/authority.guard';

@Injectable({
    providedIn: 'root',
})
export class SettingsGuard extends AuthorityGuard {

    constructor(store: Store<{}>) {
        super('SETTINGS', store);
    }

}
