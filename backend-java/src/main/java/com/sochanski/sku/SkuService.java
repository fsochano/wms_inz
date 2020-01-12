package com.sochanski.sku;

import com.google.common.base.Strings;
import com.sochanski.order.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuService {
    private final SkuRepository skuRepository;
    private final OrderLineRepository orderLineRepository;

    @Autowired
    public SkuService(SkuRepository skuRepository, OrderLineRepository orderLineRepository) {
        this.skuRepository = skuRepository;
        this.orderLineRepository = orderLineRepository;
    }

    public List<Sku> getAllSku() {
        return skuRepository.findAll();
    }

    public Sku createSku(SkuParameters params) {
        if(Strings.isNullOrEmpty(params.name)) {
            throw new BadSkuParametersException();
        }
        return skuRepository.save(new Sku(params.name, params.description));
    }

    public void deleteSku(long skuId) {
        if(!skuRepository.existsById(skuId)) {
            throw new SkuNotFoundException();
        }
        if (orderLineRepository.existsBySkuId(skuId)) {
            throw new SkuDeleteException("Sku used in order lines");
        }
        skuRepository.deleteById(skuId);
    }

}
