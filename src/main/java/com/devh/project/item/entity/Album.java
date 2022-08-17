package com.devh.project.item.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("A")
public class Album extends Item {
	private static final long serialVersionUID = 1497125298512642187L;
	private String artist;
    private String etc;
}
