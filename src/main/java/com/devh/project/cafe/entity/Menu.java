package com.devh.project.cafe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"name", "ice"}
                )
)
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
