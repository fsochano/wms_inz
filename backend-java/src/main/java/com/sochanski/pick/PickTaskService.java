package com.sochanski.pick;

import com.sochanski.container.Container;
import com.sochanski.container.ContainerRepository;
import com.sochanski.pick.data.PickTask;
import com.sochanski.pick.data.PickTaskStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PickTaskService {

    private final PickTaskRepository repository;
    private final PickListService pickListService;
    private final ContainerRepository containerRepository;

    public PickTaskService(PickTaskRepository repository,
                           PickListService pickListService,
                           ContainerRepository containerRepository) {
        this.repository = repository;
        this.pickListService = pickListService;
        this.containerRepository = containerRepository;
    }

    @Transactional
    public PickTask pick(long pickTaskId) {
        PickTask pickTask = changePickTaskStatus(pickTaskId, PickTaskStatus.READY, PickTaskStatus.PICKED);
        Container fromContainer = pickTask.getFromContainer();
        if(fromContainer.getSkuQty() < pickTask.getQty()) {
            throw new InsufficientQuantityException();
        }
        fromContainer.setSkuQty(fromContainer.getSkuQty() - pickTask.getQty());
        fromContainer.setAllocatedQty(fromContainer.getAllocatedQty() - pickTask.getQty());
        containerRepository.save(fromContainer);
        return pickTask;
    }

    @Transactional
    public PickTask complete(long pickTaskId) {
        PickTask pickTask = changePickTaskStatus(pickTaskId, PickTaskStatus.PICKED, PickTaskStatus.COMPLETED);
        Container toContainer = pickTask.getToContainer();
        long newSkuQuantity = toContainer.getSkuQty() + pickTask.getQty();
        if(toContainer.getSkuCapacity() < newSkuQuantity) {
            throw new NotEnoughSpaceInContainerException();
        }
        toContainer.setSkuQty(newSkuQuantity);
        toContainer.setFreeQty(0);
        toContainer.setAllocatedQty(toContainer.getSkuQty());
        containerRepository.save(toContainer);
        return pickTask;
    }


    @Transactional
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
