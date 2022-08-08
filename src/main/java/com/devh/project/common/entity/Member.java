package com.devh.project.common.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.devh.project.cafe.entity.Order;
import com.devh.project.common.entity.embeddable.Address;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
public class Member extends BaseMember {
	private static final long serialVersionUID = 6930767283688721121L;
	@Embedded
    private Address address;
//    @OneToMany(mappedBy = "member")
//    @Builder.Default
//    private final List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    @Builder.Default
    private final List<Order> cafeOrders = new ArrayList<>();
}
