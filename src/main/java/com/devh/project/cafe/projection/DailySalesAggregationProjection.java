package com.devh.project.cafe.projection;

import com.devh.project.cafe.dto.MenuDTO;
import org.springframework.beans.factory.annotation.Value;

public interface DailySalesAggregationProjection {
    @Value("#{target.sales}")
    Long getSales();

    @Value("#{target.menu}")
    MenuDTO getMenu();
}
