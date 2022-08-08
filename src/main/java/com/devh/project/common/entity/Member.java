package com.devh.project.common.entity;

import com.devh.project.common.entity.embeddable.Address;
import com.devh.project.shop.entity.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
public class Member extends BaseMember {
	private static final long serialVersionUID = 6930767283688721121L;
	@Embedded
    private Address address;
    @OneToMany(mappedBy = "member")
    @Builder.Default
    private final List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    @Builder.Default
    private final List<com.devh.project.cafe.entity.Order> cafeOrders = new ArrayList<>();
}
