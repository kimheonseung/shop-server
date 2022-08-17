package com.devh.project.item.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("C")
@ToString(callSuper = true)
public class Coffee extends Item {
	private static final long serialVersionUID = -5991661082734359580L;
	private boolean ice;
}
