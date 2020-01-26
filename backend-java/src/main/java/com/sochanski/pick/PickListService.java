package com.sochanski.pick;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PickListService {

    private final PickListRepository pickListRepository;

    @Autowired
    public PickListService(PickListRepository pickListRepository) {
        this.pickListRepository = pickListRepository;
    }

    public List<PickList> getPickLists() {
        return pickListRepository.findAll();
    }

    public List<PickTask> getPickTasks(long pickListId) {
        return pickListRepository.findById(pickListId)
                .map(PickList::getPickTasks)
                .orElseThrow(PickListNotFoundException::new);
    }

    public PickList updateStatus(PickList pickList) {
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
        } else {
            pickList.setStatus(PickListStatus.SHIPPED);
        }

        return pickListRepository.save(pickList);
    }
}
