package com.sochanski.container;

import com.sochanski.location.Location;
import com.sochanski.location.LocationNotFoundException;
import com.sochanski.location.LocationRepository;
import com.sochanski.pick.InsufficientQuantityException;
import com.sochanski.pick.NotEnoughSpaceInContainerException;
import com.sochanski.sku.Sku;
import com.sochanski.sku.SkuNotFoundException;
import com.sochanski.sku.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerService {
    private final ContainerRepository containerRepository;
    private final LocationRepository locationRepository;
    private final SkuRepository skuRepository;

    @Autowired
    public ContainerService(ContainerRepository containerRepository,
                            LocationRepository locationRepository,
                            SkuRepository skuRepository) {
        this.containerRepository = containerRepository;
        this.locationRepository = locationRepository;
        this.skuRepository = skuRepository;
    }


    public List<Container> getAllContainers() {
        return containerRepository.findAll();
    }

    public Container createContainer(ContainerParameters params) {
        Location location = locationRepository.findById(params.locationId)
                .orElseThrow(LocationNotFoundException::new);

        if(location.getFreeCapacity() < params.containerSize) {
            throw new NotEnoughSpaceInLocationException();
        }

        Sku sku = skuRepository.findById(params.skuId)
                .orElseThrow(SkuNotFoundException::new);

        Container container = new Container(params.type, location, params.containerSize, sku, params.skuQty, params.skuCapacity);
        return containerRepository.save(container);
    }

    public void deleteContainer(long containerId) {
        Container container = containerRepository.findById(containerId)
                .orElseThrow(ContainerNotFoundException::new);

        if(container.getSkuQty() > 0) {
            throw new NotEmptyContainerException();
        }

        containerRepository.delete(container);
    }

    public void addStock(Container container, long quantity) {
        long newSkuQuantity = container.getSkuQty() + quantity;
        if(container.getSkuCapacity() < newSkuQuantity) {
            throw new NotEnoughSpaceInContainerException();
        }
        container.setSkuQty(newSkuQuantity);
        container.setFreeQty(0);
        container.setAllocatedQty(container.getSkuQty());
        containerRepository.save(container);
    }

    public void removeStock(Container container, long quantity) {
        if(container.getSkuQty() < quantity) {
            throw new InsufficientQuantityException();
        }
        container.setSkuQty(container.getSkuQty() - quantity);
        container.setAllocatedQty(container.getAllocatedQty() - quantity);
        containerRepository.save(container);
    }
}
