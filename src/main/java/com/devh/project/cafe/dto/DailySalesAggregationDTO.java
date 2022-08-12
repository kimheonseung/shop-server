package com.devh.project.cafe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DailySalesAggregationDTO {
    MenuDTO menu;
    Long sales;
}
