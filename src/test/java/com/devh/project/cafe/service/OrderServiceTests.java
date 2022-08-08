package com.devh.project.cafe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.entity.Order;
import com.devh.project.cafe.repository.MenuRepository;
import com.devh.project.cafe.repository.OrderRepository;
import com.devh.project.common.entity.Member;
import com.devh.project.common.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private MenuRepository menuRepository;
	@Mock
	private MemberRepository memberRepository;
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
			Order savedOrder = (Order) i.getArguments()[0];
			savedOrder.setId(1234L);
			return savedOrder;
		});
		// when
		Long orderId = orderService.create(givenMemberId, givenMenuId, givenCount);
		// then
		assertEquals(orderId, 1234L);
	}
	
}