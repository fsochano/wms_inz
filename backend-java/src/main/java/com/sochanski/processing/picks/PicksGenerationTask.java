package com.sochanski.processing.picks;

import com.sochanski.container.Container;
import com.sochanski.container.ContainerRepository;
import com.sochanski.container.ContainerType;
import com.sochanski.location.Location;
import com.sochanski.location.LocationRepository;
import com.sochanski.location.LocationType;
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

import java.util.Comparator;

@Builder
public class PicksGenerationTask extends TransactionCallbackWithoutResult {

    private final long orderHeaderId;

    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderLineRepository orderLineRepository;
    private final PickListRepository pickListRepository;
    private final PickTaskRepository pickTaskRepository;
    private final ContainerRepository containerRepository;
    private final LocationRepository locationRepository;

    private PicksGenerationTask(long orderHeaderId,
                                OrderHeaderRepository orderHeaderRepository,
                                OrderLineRepository orderLineRepository,
                                PickListRepository pickListRepository,
                                PickTaskRepository pickTaskRepository,
                                ContainerRepository containerRepository,
                                LocationRepository locationRepository) {
        this.orderHeaderId = orderHeaderId;
        this.orderHeaderRepository = orderHeaderRepository;
        this.orderLineRepository = orderLineRepository;
        this.pickListRepository = pickListRepository;
        this.pickTaskRepository = pickTaskRepository;
        this.containerRepository = containerRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    protected void doInTransactionWithoutResult(TransactionStatus ts) {
        var orderHeader = orderHeaderRepository.findById(orderHeaderId)
                .orElseThrow(OrderHeaderNotFoundException::new);

        var pickList = pickListRepository.save(new PickList(orderHeader));
        orderHeader.getOrderLines().forEach(ol -> createPickTask(ol, pickList));
    }

    private void createPickTask(OrderLine ol, PickList pickList) {
        var qtyToFill = ol.getQty() - ol.getAllocated();

        var location = locationRepository.findAll()
                .stream()
                .filter(l -> LocationType.SHIPDOCK.equals(l.getLocationType()) && l.getFreeCapacity() > 0)
                .min(Comparator.comparing(Location::getFreeCapacity))
                .orElseThrow();

        var toContainer = new Container(ContainerType.SHIPPING, location, 1, ol.getSku(), 0, qtyToFill);
        containerRepository.save(toContainer);

        var fromContainers = containerRepository.findBySkuAndFreeQtyGreaterThan0AndTypeEqualStorageOrderByFreeQtyAsc(ol.getSku());
        for (var fromContainer : fromContainers) {
            if (fromContainer.getSkuQty() < qtyToFill) {
                allocateQuantity(ol, pickList, fromContainer, toContainer, fromContainer.getSkuQty());
                qtyToFill -= fromContainer.getSkuQty();
            } else {
                allocateQuantity(ol, pickList, fromContainer, toContainer, qtyToFill);
                break;
            }
        }
    }

    private void allocateQuantity(OrderLine ol, PickList pickList, Container fromContainer, Container toContainer, long allocatedQuantity) {
        fromContainer.setAllocatedQty(fromContainer.getAllocatedQty() + allocatedQuantity);
        fromContainer.setFreeQty(fromContainer.getSkuQty() - fromContainer.getAllocatedQty());
        containerRepository.save(fromContainer);
        ol.setAllocated(ol.getAllocated() + allocatedQuantity);
        orderLineRepository.save(ol);
        pickTaskRepository.save(new PickTask(pickList, ol, allocatedQuantity, fromContainer, toContainer));
    }
}
