package com.devh.project.cafe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CAFE_ORDER_MENU")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenu {
	@Id @GeneratedValue
	@Column(name = "CAFE_ORDER_MENU_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_ID")
	private Menu menu;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAFE_ORDER_ID")
	private Order order;
	
	private int count;

	public static OrderMenu create(Menu menu, int count) {
		return OrderMenu.builder()
				.menu(menu)
				.count(count)
				.build();
	}
	
	public long getTotalPrice() {
		return getMenu().getPrice() * count;
	}
}
