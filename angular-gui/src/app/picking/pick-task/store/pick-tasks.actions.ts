import { createAction, props } from '@ngrx/store';
import { PickTask } from '../pick-tasks.model';

class Actions{

    readonly pickTaksRequested = createAction(
        '[Pick Tasks component] Pick Tasks requested',
        props<{ pickListId: number }>(),
    );

    readonly pickTasksLoaded = createAction(
        '[Pick Tasks component] Pick Tasks Loaded',
        props<{ pickTasks: PickTask[] }>(),
    );

    readonly pickTaskUpdated = createAction(
        '[Pick Tasks component] Pick Tasks updated',
        props<{ pickTask: PickTask }>(),
    );

}

export const PickTasksActions = new Actions();