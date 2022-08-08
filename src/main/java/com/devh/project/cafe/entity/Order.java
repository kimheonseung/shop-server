package com.devh.project.cafe.entity;

import com.devh.project.cafe.constant.OrderStatus;
import com.devh.project.common.entity.Member;
import groovy.transform.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CAFE_ORDER")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	@Id @Generated
	@Column(name = "CAFE_ORDER_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
	private final List<OrderMenu> orderMenuList = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public static Order create(Member member, OrderMenu... orderMenus) {
		Order order = new Order();
		order.setMember(member);
		for(OrderMenu orderMenu : orderMenus) {
			order.addOrderMenu(orderMenu);
		}
		order.setStatus(OrderStatus.ORDERED);
		return order;
	}
	
	public void setMember(Member member) {
		this.member = member;
		member.getCafeOrders().add(this);
	}
	public void addOrderMenu(OrderMenu orderMenu) {
		orderMenuList.add(orderMenu);
		orderMenu.setOrder(this);
	}
}
