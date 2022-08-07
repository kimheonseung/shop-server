package com.devh.project.cafe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class MenuDTO {
    private Long id;
    private String name;
    private long price;
    private boolean ice;
    private boolean onSale;

    public static MenuDTO create(String name, long price, boolean ice, boolean onSale) {
        return MenuDTO.builder()
                .name(name)
                .price(price)
                .ice(ice)
                .onSale(onSale)
                .build();
    }
    public static MenuDTO create(Long id, String name, long price, boolean ice, boolean onSale) {
        return MenuDTO.builder()
                .id(id)
                .name(name)
                .price(price)
                .ice(ice)
                .onSale(onSale)
                .build();
    }
}
