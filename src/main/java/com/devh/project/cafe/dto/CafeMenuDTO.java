package com.devh.project.cafe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CafeMenuDTO {
    private Long id;
    private String name;
    private long price;
    private boolean onSale;
}
