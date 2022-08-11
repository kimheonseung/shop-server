package com.devh.project.cafe.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.devh.project.cafe.constant.OrderStatus;
import com.devh.project.cafe.exception.CafeOrderServiceException;
import com.devh.project.common.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CAFE_ORDER")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	@Transient
	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	@Id @GeneratedValue
	@Column(name = "CAFE_ORDER_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private final List<OrderMenu> orderMenuList = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private String date;
	
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
		order.setDate(DF.format(System.currentTimeMillis()));
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
	public void cancel() {
		if(!OrderStatus.ORDERED.equals(this.getStatus()))
			throw new CafeOrderServiceException("취소할 수 없는 주문 상태입니다.");
		this.setStatus(OrderStatus.CANCELED);
	}
}
