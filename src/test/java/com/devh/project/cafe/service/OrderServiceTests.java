package com.devh.project.cafe.service;

import com.devh.project.cafe.entity.DailySales;
import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.entity.Order;
import com.devh.project.cafe.repository.DailySalesRepository;
import com.devh.project.cafe.repository.MenuRepository;
import com.devh.project.cafe.repository.OrderRepository;
import com.devh.project.common.entity.Member;
import com.devh.project.common.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private MenuRepository menuRepository;
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private DailySalesRepository dailySalesRepository;
	@InjectMocks
	private OrderService orderService;
	
	@Test
	public void create() {
		// given
		final Long givenMemberId = 1L;
		final Long givenMenuId = 1L;
		final int givenCount = 3;
		final Member givenMember = Member.builder()
				.id(givenMemberId)
				.username("username")
				.password("password")
				.build();
		final Menu givenMenu = Menu.create(givenMenuId, "Americano", 1111, true, true);
		given(memberRepository.findById(givenMenuId)).willReturn(Optional.of(givenMember));
		given(menuRepository.findById(givenMenuId)).willReturn(Optional.of(givenMenu));
		given(orderRepository.save(any(Order.class))).willAnswer(i -> {
			Order order = (Order) i.getArguments()[0];
			order.setId(1234L);
			return order;
		});
		given(dailySalesRepository.findByDateAndMenu(any(String.class), any(Menu.class))).willReturn(Optional.empty());
		given(dailySalesRepository.save(any(DailySales.class))).willAnswer(i -> i.getArguments()[0]);
		// when
		Long orderId = orderService.create(givenMemberId, givenMenuId, givenCount);
		// then
		assertEquals(orderId, 1234L);
	}

	@Test
	public void createBulk() {
		// given
		final Long givenMemberId = 1L;
		final List<Long> givenMenuIdList = List.of(1L, 2L, 3L);
		final List<Integer> givenCountList = List.of(3, 2, 7);
		final Member givenMember = Member.builder()
				.id(givenMemberId)
				.username("username")
				.password("password")
				.build();
		final Menu givenMenu1 = Menu.create(1L, "Americano", 1111, true, true);
		final Menu givenMenu2 = Menu.create(2L, "Latte", 2222, true, true);
		final Menu givenMenu3 = Menu.create(3L, "Mocha", 3333, true, true);
		given(memberRepository.findById(1L)).willReturn(Optional.of(givenMember));
		given(menuRepository.findById(1L)).willReturn(Optional.of(givenMenu1));
		given(menuRepository.findById(2L)).willReturn(Optional.of(givenMenu2));
		given(menuRepository.findById(3L)).willReturn(Optional.of(givenMenu3));
		given(orderRepository.save(any(Order.class))).willAnswer(i -> {
			Order order = (Order) i.getArguments()[0];
			order.setId(1234L);
			return order;
		});
		given(dailySalesRepository.findByDateAndMenu(any(String.class), any(Menu.class))).willReturn(Optional.empty());
		given(dailySalesRepository.save(any(DailySales.class))).willAnswer(i -> i.getArguments()[0]);
		// when
		Long orderId = orderService.create(givenMemberId, givenMenuIdList, givenCountList);
		// then
		assertEquals(orderId, 1234L);
	}
	
}
