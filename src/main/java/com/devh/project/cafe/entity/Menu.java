package com.devh.project.cafe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"name", "ice"}
                ),
        name = "CAFE_MENU"
)
@Builder
@Getter
@Setter
public class Menu {
    @Id @GeneratedValue
    @Column(name = "CAFE_MENU_ID")
    private Long id;
    private String name;
    private long price;
    private boolean ice;
    private boolean onSale;

    public static Menu create(String name, long price, boolean ice, boolean onSale) {
        return Menu.builder()
                .name(name)
                .price(price)
                .ice(ice)
                .onSale(onSale)
                .build();
    }
    public static Menu create(Long id, String name, long price, boolean ice, boolean onSale) {
        return Menu.builder()
                .id(id)
                .name(name)
                .price(price)
                .ice(ice)
                .onSale(onSale)
                .build();
    }
}
