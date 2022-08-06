package com.devh.project.item.constant;

import lombok.Getter;

@Getter
public enum ItemDiscriminator {
    ALBUM("A"),
    BOOK("B"),
    COFFEE("C"),
    MOVIE("M");
    private final String value;
    ItemDiscriminator(String value) {
        this.value = value;
    }
}
