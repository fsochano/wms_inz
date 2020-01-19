import { Container } from './containers.model';
import { createAction, props } from '@ngrx/store';

class Actions {
    readonly containersLoaded = createAction(
        '[Containers] Containers loaded',
        props<{ containers: Container[] }>(),
    );
    readonly containerCreated = createAction(
        '[Containers] Container created',
        props<{ container: Container }>(),
    )
}

export const ContainersActions = new Actions();