package com.devh.project.cafe.controller;

import com.devh.project.cafe.projection.DailySalesAggregationProjection;
import com.devh.project.cafe.service.DailySalesService;
import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/cafe/daily-sales")
@RequiredArgsConstructor
public class DailySalesController {
    private final DailySalesService dailySalesService;
    @GetMapping("/top")
    public ApiResponseDTO<DailySalesAggregationProjection> getAggregate(
            @RequestParam(value = "top", required = false, defaultValue = "3") int top,
            @RequestParam(value = "days", required = false, defaultValue = "7") int days) {
        log.info("[GET] /cafe/daily-sales/aggregate ... days: "+days+", top: "+top);
        return ApiResponseDTO.success(ApiStatus.Success.OK, dailySalesService.top(days, top));
    }
}
