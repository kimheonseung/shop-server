package com.devh.project.shop.dto;

import com.devh.project.shop.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchResponseDTO {
    private List<Order> orderList;
}
