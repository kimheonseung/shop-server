package com.devh.project.cafe.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.devh.project.cafe.entity.DailySales;
import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.entity.Order;
import com.devh.project.cafe.entity.OrderMenu;
import com.devh.project.cafe.repository.DailySalesRepository;
import com.devh.project.cafe.repository.MenuRepository;
import com.devh.project.cafe.repository.OrderRepository;
import com.devh.project.common.entity.Member;
import com.devh.project.common.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final MenuRepository menuRepository;
	private final DailySalesRepository dailySalesRepository;
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public Long create(Long memberId, Long menuId, int count) {
		final String nowDate = sdf.format(new Date());
		
		// find member
		Member member = memberRepository.findById(memberId).orElseThrow();
		
		// find menu
		Menu menu = menuRepository.findById(menuId).orElseThrow();
		
		// create order menu
		OrderMenu orderMenu = OrderMenu.create(menu, count);
		
		// create order
		Order order = orderRepository.save(Order.create(member, orderMenu));
		
		// update daily sales
		DailySales dailySales = dailySalesRepository.findByDateAndMenu(nowDate, menu)
				.orElseGet(() -> dailySalesRepository.save(DailySales.create(nowDate, menu)));
		dailySales.addSales(count);
		
		return order.getId();
	}
}
