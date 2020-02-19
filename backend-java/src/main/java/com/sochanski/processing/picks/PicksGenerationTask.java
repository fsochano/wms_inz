package com.sochanski.processing.picks;

import com.sochanski.container.Container;
import com.sochanski.container.ContainerRepository;
import com.sochanski.order.OrderHeaderNotFoundException;
import com.sochanski.order.OrderHeaderRepository;
import com.sochanski.order.OrderLineRepository;
import com.sochanski.order.data.OrderLine;
import com.sochanski.pick.PickListRepository;
import com.sochanski.pick.PickTaskRepository;
import com.sochanski.pick.data.PickList;
import com.sochanski.pick.data.PickTask;
import lombok.Builder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

@Builder
public class PicksGenerationTask extends TransactionCallbackWithoutResult {

    private final long orderHeaderId;

    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderLineRepository orderLineRepository;
    private final PickListRepository pickListRepository;
    private final PickTaskRepository pickTaskRepository;
    private final ContainerRepository containerRepository;

    private PicksGenerationTask(long orderHeaderId,
                               OrderHeaderRepository orderHeaderRepository,
                               OrderLineRepository orderLineRepository,
                               PickListRepository pickListRepository,
                               PickTaskRepository pickTaskRepository,
                               ContainerRepository containerRepository) {
        this.orderHeaderId = orderHeaderId;
        this.orderHeaderRepository = orderHeaderRepository;
        this.orderLineRepository = orderLineRepository;
        this.pickListRepository = pickListRepository;
        this.pickTaskRepository = pickTaskRepository;
        this.containerRepository = containerRepository;
    }

    @Override
    protected void doInTransactionWithoutResult(TransactionStatus ts) {
        var orderHeader = orderHeaderRepository.findById(orderHeaderId)
                .orElseThrow(OrderHeaderNotFoundException::new);

        var pickList = pickListRepository.save(new PickList(orderHeader));
        orderHeader.getOrderLines().forEach(ol -> createPickTask(ol, pickList));
    }

    private void createPickTask(OrderLine ol, PickList pickList) {
        var containers = containerRepository.findBySkuAndFreeQtyGreaterThan0OrderByFreeQtyAsc(ol.getSku());
        var qtyToFill = ol.getQty() - ol.getAllocated();
        for (var container : containers) {
            var allocatingQuantity = container.getSkuQty() - qtyToFill;
            if (allocatingQuantity < 0) {
                allocateQuantity(ol, pickList, container, allocatingQuantity);
                qtyToFill -= allocatingQuantity;
            } else {
                allocateQuantity(ol, pickList, container, qtyToFill);
                break;
            }
        }
    }

    private void allocateQuantity(OrderLine ol, PickList pickList, Container container, long allocatedQuantity) {
        container.setAllocatedQty(container.getAllocatedQty() + allocatedQuantity);
        container.setFreeQty(container.getSkuQty() - container.getAllocatedQty());
        containerRepository.save(container);
        ol.setAllocated(ol.getAllocated() + allocatedQuantity);
        orderLineRepository.save(ol);
        pickTaskRepository.save(new PickTask(pickList, ol, allocatedQuantity, container));
    }
}
