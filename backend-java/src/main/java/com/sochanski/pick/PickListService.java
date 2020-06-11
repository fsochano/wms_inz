package com.sochanski.pick;

import com.sochanski.container.ContainerService;
import com.sochanski.order.OrderHeaderService;
import com.sochanski.pick.data.PickList;
import com.sochanski.pick.data.PickListStatus;
import com.sochanski.pick.data.PickTask;
import com.sochanski.pick.data.PickTaskStatus;
import com.sochanski.shipping.Shipment;
import com.sochanski.shipping.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PickListService {

    private final PickListRepository pickListRepository;
    private final PickTaskRepository pickTaskRepository;
    private final OrderHeaderService orderHeaderService;
    private final ContainerService containerService;
    private final ShippingRepository shipmentRepository;

    @Autowired
    public PickListService(PickListRepository pickListRepository, PickTaskRepository pickTaskRepository, OrderHeaderService orderHeaderService, ContainerService containerService, ShippingRepository shipmentRepository) {
        this.pickListRepository = pickListRepository;
        this.pickTaskRepository = pickTaskRepository;
        this.orderHeaderService = orderHeaderService;
        this.containerService = containerService;
        this.shipmentRepository = shipmentRepository;
    }

    public List<PickList> getPickLists() {
        return pickListRepository.findAll();
    }

    public List<PickTask> getPickTasks(long pickListId) {
        return pickListRepository.findById(pickListId).map(PickList::getPickTasks)
                .orElseThrow(PickListNotFoundException::new);
    }

    @Transactional
    public PickTask pick(long pickTaskId) {
        PickTask pickTask = changePickTaskStatus(pickTaskId, PickTaskStatus.READY, PickTaskStatus.PICKED);
        updatePickListStatus(pickTask.getPickList());
        containerService.removeStock(pickTask.getFromContainer(), pickTask.getQty());
        return pickTask;
    }

    @Transactional
    public PickTask complete(long pickTaskId) {
        PickTask pickTask = changePickTaskStatus(pickTaskId, PickTaskStatus.PICKED, PickTaskStatus.COMPLETED);
        updatePickListStatus(pickTask.getPickList());
        containerService.addStock(pickTask.getToContainer(), pickTask.getQty());
        return pickTask;
    }

    @Transactional
    public PickList ship(long pickListId) {
        return pickListRepository.findById(pickListId)
            .map(pickList -> {
                pickList.getPickTasks().forEach(pickTask -> pickTask.setStatus(PickTaskStatus.SHIPPED));
                pickTaskRepository.saveAll(pickList.getPickTasks());
                return updatePickListStatus(pickList);
            })
            .orElseThrow(PickListNotFoundException::new);
    }

    private PickList updatePickListStatus(PickList pickList) {
        var orders = pickList.getPickTasks()
                .stream()
                .map(PickTask::getStatus)
                .map(PickTaskStatus::getOrder)
                .collect(Collectors.toList());
        int min = PickTaskStatus.SHIPPED.getOrder();
        int max = PickTaskStatus.READY.getOrder();
        for (var order : orders) {
            min = Math.min(min, order);
            max = Math.max(max, order);
        }

        if (min <= PickTaskStatus.PICKED.getOrder() && max >= PickTaskStatus.PICKED.getOrder()) {
            pickList.setStatus(PickListStatus.IN_PROGRESS);
        } else if (min == PickTaskStatus.COMPLETED.getOrder() && max >= PickTaskStatus.COMPLETED.getOrder()) {
            pickList.setStatus(PickListStatus.COMPLETED);
            createShipment(pickList);
            orderHeaderService.completeOrder(pickList.getOrderId());
        } else {
            pickList.setStatus(PickListStatus.SHIPPED);
            orderHeaderService.shipOrder(pickList.getOrderId());
        }

        return pickListRepository.save(pickList);
    }

    private PickTask changePickTaskStatus(long pickTaskId, PickTaskStatus from, PickTaskStatus to) {
        return pickTaskRepository.findById(pickTaskId)
                .map(pickTask -> {
                    if (pickTask.getStatus() != from) {
                        throw new CannotChangePickTaskStatusException(from, to);
                    }
                    pickTask.setStatus(to);
                    return pickTaskRepository.save(pickTask);
                })
                .orElseThrow(PickTaskNotFoundException::new);
    }

    private Shipment createShipment(@NotNull PickList pickList) {
        if (pickList == null) {
            throw new IllegalArgumentException(); // TODO
        }
        return shipmentRepository.save(new Shipment(pickList));
    }

}
