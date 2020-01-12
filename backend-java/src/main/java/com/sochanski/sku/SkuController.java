package com.sochanski.sku;

import com.sochanski.ApiUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH + "/sku")
@CrossOrigin
public class SkuController {

    private final SkuService service;

    public SkuController(SkuService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sku> getAllSku() {
        return service.getAllSku();
    }

    @PostMapping
    public Sku createSku(@RequestBody @Valid SkuParameters params) {
        return service.createSku(params);
    }

    @DeleteMapping(path = "/{skuId}")
    public void deleteSku(@PathVariable long skuId) {
        service.deleteSku(skuId);
    }
}
