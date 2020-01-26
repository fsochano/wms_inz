package com.sochanski.pick;

import org.springframework.stereotype.Service;

@Service
public class PickTaskService {

    private final PickTaskRepository repository;
    private final PickListService pickListService;

    public PickTaskService(PickTaskRepository repository, PickListService pickListService) {
        this.repository = repository;
        this.pickListService = pickListService;
    }

    public PickTask pickPickTask(long pickTaskId) {
        return changePickTaskStatus(pickTaskId, PickTaskStatus.READY, PickTaskStatus.PICKED);
    }

    public PickTask completePickTask(long pickTaskId) {
        return changePickTaskStatus(pickTaskId, PickTaskStatus.PICKED, PickTaskStatus.COMPLETED);
    }

    public PickTask changePickTaskStatus(long pickTaskId, PickTaskStatus status) {
        return changePickTaskStatus(pickTaskId, findPickFromPickStatus(status), status);
    }

    private PickTaskStatus findPickFromPickStatus(PickTaskStatus status) {
        switch (status) {
            case READY:
            case PICKED:
                return PickTaskStatus.READY;
            case COMPLETED:
                return PickTaskStatus.PICKED;
            case SHIPPED:
                return PickTaskStatus.COMPLETED;
            default:
                throw new CannotChangePickTaskStatusException(status, null);
        }
    }

    private PickTask changePickTaskStatus(long pickTaskId, PickTaskStatus from, PickTaskStatus to) {
        return repository.findById(pickTaskId)
                .map(pickTask -> {
                    if (pickTask.getStatus() != from) {
                        throw new CannotChangePickTaskStatusException(from, to);
                    }
                    pickTask.setStatus(to);
                    return pickTask;
                })
                .map(repository::save)
                .map(pickTask -> {
                    pickListService.updateStatus(pickTask.getPickList());
                    return pickTask;
                })
                .orElseThrow(PickTaskNotFoundException::new);
    }

}
