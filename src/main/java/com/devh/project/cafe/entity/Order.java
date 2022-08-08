package com.devh.project.cafe.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.devh.project.cafe.constant.OrderStatus;
import com.devh.project.common.entity.Member;

import groovy.transform.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CAFE_ORDER")
@Builder
@Setter
@Getter
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
	@Builder.Default
	private final List<OrderMenu> orderMenuList = new ArrayList<OrderMenu>();
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
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
