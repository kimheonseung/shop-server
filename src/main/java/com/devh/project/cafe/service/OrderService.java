package com.devh.project.cafe.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devh.project.cafe.constant.OrderStatus;
import com.devh.project.cafe.entity.DailySales;
import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.entity.Order;
import com.devh.project.cafe.entity.OrderMenu;
import com.devh.project.cafe.exception.CafeOrderServiceException;
import com.devh.project.cafe.repository.DailySalesRepository;
import com.devh.project.cafe.repository.MenuRepository;
import com.devh.project.cafe.repository.OrderRepository;
import com.devh.project.common.entity.Member;
import com.devh.project.common.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
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

	public Long create(Long memberId, List<Long> menuIdList, List<Integer> countList) {
		final String nowDate = sdf.format(new Date());
		final int menuSize = menuIdList.size();
		final int countSize = countList.size();

		// check pair
		if(menuSize != countSize)
			throw new CafeOrderServiceException("menu ????????? ???????????? ????????????.");

		// find member
		Member member = memberRepository.findById(memberId).orElseThrow();

		OrderMenu[] orderMenuArray = new OrderMenu[menuSize];

		for(int i = 0 ; i < menuSize ; ++i) {
			// find menu
			Menu menu = menuRepository.findById(menuIdList.get(i)).orElseThrow();
			// create order menu
			orderMenuArray[i] = OrderMenu.create(menu, countList.get(i));
		}

		// create order
		Order order = orderRepository.save(Order.create(member, orderMenuArray));

		for (int i = 0; i < orderMenuArray.length; ++i) {
			Menu menu = orderMenuArray[i].getMenu();
			// update daily sales
			DailySales dailySales = dailySalesRepository.findByDateAndMenu(nowDate, menu)
					.orElseGet(() -> dailySalesRepository.save(DailySales.create(nowDate, menu)));
			dailySales.addSales(countList.get(i));
		}

		return order.getId();
	}

	public boolean delete(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow();
		if(!OrderStatus.CANCELED.equals(order.getStatus()))
			throw new CafeOrderServiceException("????????? ????????? ????????? ??? ????????????.");
		orderRepository.deleteById(orderId);
		return true;
	}
	
	public boolean cancel(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow();
		order.cancel();
		
		final String date = order.getDate().substring(0, 10);
		
		List<OrderMenu> orderMenuList = order.getOrderMenuList();
		orderMenuList.forEach(orderMenu -> {
			DailySales dailySales = dailySalesRepository.findByDateAndMenu(date, orderMenu.getMenu()).orElseThrow();
			dailySales.cancelSales(orderMenu.getCount());
		});
		
		return true;
	}
}
