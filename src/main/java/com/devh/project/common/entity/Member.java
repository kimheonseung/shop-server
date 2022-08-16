package com.devh.project.common.entity;

import com.devh.project.cafe.entity.Order;
import com.devh.project.common.entity.embeddable.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
//    @OneToMany(mappedBy = "member")
//    @Builder.Default
//    private final List<Order> orders = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    @Builder.Default
    private final List<Order> cafeOrders = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private final List<Authority> authorities = new ArrayList<>();

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
}
