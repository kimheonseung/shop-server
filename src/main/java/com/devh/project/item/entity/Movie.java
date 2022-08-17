package com.devh.project.item.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("M")
public class Movie extends Item {
	private static final long serialVersionUID = 2151838464817260268L;
	private String director;
    private String actor;
}
