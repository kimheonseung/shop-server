package com.devh.project.cafe.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devh.project.cafe.dto.OrderCancelRequestDTO;
import com.devh.project.cafe.dto.OrderCancelResponseDTO;
import com.devh.project.cafe.dto.OrderCreateRequestDTO;
import com.devh.project.cafe.dto.OrderCreateResponseDTO;
import com.devh.project.cafe.dto.OrderDeleteRequestDTO;
import com.devh.project.cafe.dto.OrderDeleteResponseDTO;
import com.devh.project.cafe.service.OrderService;
import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cafe/order")
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ApiResponseDTO<OrderCreateResponseDTO> createOrder(@RequestBody OrderCreateRequestDTO orderCreateRequestDTO) {
        log.info("/cafe/order/create ... "+orderCreateRequestDTO);
        Long orderId = orderService.create(orderCreateRequestDTO.getMemberId(), orderCreateRequestDTO.getMenuIdList(), orderCreateRequestDTO.getCountList());
        return ApiResponseDTO.success(ApiStatus.Success.OK, OrderCreateResponseDTO.builder().id(orderId).build());
    }

    @PostMapping("/delete")
    public ApiResponseDTO<OrderDeleteResponseDTO> deleteOrder(@RequestBody OrderDeleteRequestDTO orderDeleteRequestDTO) {
        log.info("/cafe/order/delete ... "+orderDeleteRequestDTO);
        boolean result = orderService.delete(orderDeleteRequestDTO.getId());
        return ApiResponseDTO.success(ApiStatus.Success.OK, OrderDeleteResponseDTO.builder().result(result).build());
    }
    
    @PostMapping("/cancel")
    public ApiResponseDTO<OrderCancelResponseDTO> cancelOrder(@RequestBody OrderCancelRequestDTO orderCancelRequestDTO) {
    	log.info("/cafe/order/cancel ... "+orderCancelRequestDTO);
    	boolean result = orderService.cancel(orderCancelRequestDTO.getId());
    	return ApiResponseDTO.success(ApiStatus.Success.OK, OrderCancelResponseDTO.builder().result(result).build());
    	
    }
}
