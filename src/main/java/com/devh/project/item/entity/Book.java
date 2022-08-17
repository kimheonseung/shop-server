package com.devh.project.item.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("B")
public class Book extends Item {
	private static final long serialVersionUID = -2268922152488135700L;
	private String author;
    private String isbn;
}
