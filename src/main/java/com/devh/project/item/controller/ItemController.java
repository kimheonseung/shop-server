package com.devh.project.item.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponseDTO;
import com.devh.project.item.dto.CoffeeCreateRequestDTO;
import com.devh.project.item.dto.CoffeeCreateResponseDTO;
import com.devh.project.item.dto.CoffeeSearchRequestDTO;
import com.devh.project.item.dto.CoffeeSearchResponseDTO;
import com.devh.project.item.dto.CoffeeStockRequestDTO;
import com.devh.project.item.entity.Coffee;
import com.devh.project.item.service.CoffeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final CoffeeService coffeeService;

    @PostMapping("/coffee/create")
    public ApiResponseDTO<CoffeeCreateResponseDTO> createCoffee(@RequestBody CoffeeCreateRequestDTO coffeeCreateRequestDTO) throws Exception {
        log.info("[POST] /item/coffee/create \n\t"+coffeeCreateRequestDTO.toString());
        CoffeeCreateResponseDTO itemCreateResponseDTO = coffeeService.create(coffeeCreateRequestDTO);
        return ApiResponseDTO.success(ApiStatus.Success.OK, itemCreateResponseDTO);
    }

    @GetMapping("/coffee")
    public ApiResponseDTO<Coffee> getCoffee(CoffeeSearchRequestDTO coffeeSearchRequestDTO) throws Exception {
        log.info("[GET] /item/coffee \n\t"+coffeeSearchRequestDTO.toString());
        CoffeeSearchResponseDTO coffeeSearchResponseDTO = coffeeService.search(coffeeSearchRequestDTO);
        return ApiResponseDTO.success(ApiStatus.Success.OK, coffeeSearchResponseDTO.getItemList(), coffeeSearchResponseDTO.getPaging());
    }

    @PostMapping("/coffee/stock")
    public ApiResponseDTO<Boolean> coffeeAddStock(@RequestBody CoffeeStockRequestDTO coffeeStockRequestDTO) throws Exception {
        log.info("[POST] /item/coffee/stock \n\t"+coffeeStockRequestDTO);
        coffeeService.modifyStock(coffeeStockRequestDTO);
        return ApiResponseDTO.success(ApiStatus.Success.OK, true);
    }

}
