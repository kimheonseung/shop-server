package com.devh.project.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequestDTO {
    private Long userId;
    private Long itemId;
    private int count;
}
