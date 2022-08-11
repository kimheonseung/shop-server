package com.devh.project.cafe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
	
	@Test
	public void delete_notCanceled() {
		// given
		final long givenOrderId = 1L;
		Order givenOrder = Order.create(Member.builder().id(1L).build(), new OrderMenu[] {});
		givenOrder.setId(givenOrderId);
		givenOrder.setStatus(OrderStatus.COMPLETED);
		given(orderRepository.findById(givenOrderId)).willReturn(Optional.of(givenOrder));
		// then
		assertThrows(CafeOrderServiceException.class, () -> orderService.delete(givenOrderId), "취소된 주문만 삭제할 수 있습니다.");
	}
	
	@Test
	public void delete_canceled() {
		// given
		final long givenOrderId = 1L;
		Order givenOrder = Order.create(Member.builder().id(1L).build(), new OrderMenu[] {});
		givenOrder.setId(givenOrderId);
		givenOrder.setStatus(OrderStatus.CANCELED);
		given(orderRepository.findById(givenOrderId)).willReturn(Optional.of(givenOrder));
		doNothing().when(orderRepository).deleteById(givenOrderId);
		// when
		boolean result = orderService.delete(givenOrderId);
		// then
		assertTrue(result);
	}
	
	@Test
	public void cancel_notOrdered() {
		// given
		final long givenOrderId = 1L;
		Order givenOrder = Order.create(Member.builder().id(1L).build(), new OrderMenu[] {});
		givenOrder.setId(givenOrderId);
		givenOrder.setStatus(OrderStatus.STARTED);
		given(orderRepository.findById(givenOrderId)).willReturn(Optional.of(givenOrder));
		// then
		assertThrows(CafeOrderServiceException.class, () -> orderService.delete(givenOrderId), "취소할 수 없는 주문 상태입니다.");
	}
	
	@Test
	public void cancel_ordered() {
		// given
		final long givenOrderId = 1L;
		final int defaultSales1 = 103;
		final int defaultSales2 = 207;
		final int givenCount1 = 3;
		final int givenCount2 = 7;
		final String givenDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()).substring(0, 10);
		Menu givenMenu1 = Menu.create(1L, "Americano", 1111, true, true);
		OrderMenu givenOrderMenu1 = OrderMenu.create(givenMenu1, givenCount1);
		DailySales givenDailySales1 = DailySales.create(givenDate, givenMenu1);
		givenDailySales1.addSales(defaultSales1);
		Menu givenMenu2 = Menu.create(2L, "Latte", 2222, true, true);
		OrderMenu givenOrderMenu2 = OrderMenu.create(givenMenu2, givenCount2);
		DailySales givenDailySales2 = DailySales.create(givenDate, givenMenu2);
		givenDailySales2.addSales(defaultSales2);
		Order givenOrder = Order.create(Member.builder().id(1L).build(), givenOrderMenu1, givenOrderMenu2);
		givenOrder.setId(givenOrderId);
		given(orderRepository.findById(givenOrderId)).willReturn(Optional.of(givenOrder));
		given(dailySalesRepository.findByDateAndMenu(givenDate, givenMenu1)).willReturn(Optional.of(givenDailySales1));
		given(dailySalesRepository.findByDateAndMenu(givenDate, givenMenu2)).willReturn(Optional.of(givenDailySales2));
		// when
		boolean result = orderService.cancel(givenOrderId);
		// then
		assertEquals(givenDailySales1.getSales(), defaultSales1 - givenCount1);
		assertEquals(givenDailySales2.getSales(), defaultSales2 - givenCount2);
		assertTrue(result);
	}
}
