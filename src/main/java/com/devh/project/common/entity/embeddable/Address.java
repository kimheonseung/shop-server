package com.devh.project.common.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String zipcode;
    private String city;
    private String gu;
    private String street;
    private String rest;

    @Override
    public String toString() {
        return String.format("[%s] %s시 %s구 %s %s", zipcode, city, gu, street, rest);
    }
}
