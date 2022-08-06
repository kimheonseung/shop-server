package com.devh.project.cafe.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Setter
@NoArgsConstructor
public class CafeMenu {
    @Id @GeneratedValue
    @Column(name = "CAFE_MENU_ID")
    private Long id;
    private String name;
    private long price;
    private boolean onSale;
}
