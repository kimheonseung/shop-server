package com.devh.project.shop.service;

import com.devh.project.common.entity.Member;
import com.devh.project.common.repository.MemberRepository;
import com.devh.project.shop.entity.Delivery;
import com.devh.project.shop.entity.Order;
import com.devh.project.shop.entity.OrderItem;
import com.devh.project.shop.entity.OrderSearch;
import com.devh.project.shop.entity.item.Item;
import com.devh.project.shop.repository.ItemRepository;
import com.devh.project.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public Long order(Long userId, Long itemId, int count) {
        Member member = memberRepository.findById(userId).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();

        Delivery delivery = new Delivery(member.getAddress());
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = orderRepository.save(
                Order.createOrder(member, delivery, orderItem)
        );
        System.out.printf("%s의 주문 - %s [%d 원] %d개 \n배송지: %s", order.getMember().getUsername(), item.getName(), item.getPrice(), count, order.getDelivery().getAddress().toString());
        return order.getId();
    }

    public void cancel(Long orderId) {
        orderRepository.findById(orderId).orElseThrow().cancel();
    }

    public List<Order> getOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch.toSpecification());
    }
}
