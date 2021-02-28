package com.jm.marketplace.controller.rest;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/goods")
public class AdvertisementRestController {

    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementRestController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<AdvertisementDto> getAll() {
        return advertisementService.findAll();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AdvertisementDto getById(@PathVariable(name = "id") Long id) {
        return advertisementService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid AdvertisementDto advertisementDto) {
        advertisementService.saveOrUpdate(advertisementDto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@RequestBody @Valid AdvertisementDto advertisementDto) {
        advertisementService.saveOrUpdate(advertisementDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(name = "id") Long id) {
        advertisementService.deleteById(id);
    }
}
