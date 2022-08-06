package com.devh.project.shop.service;

import com.devh.project.common.entity.Member;
import com.devh.project.common.repository.MemberRepository;
import com.devh.project.shop.dto.OrderCancelRequestDTO;
import com.devh.project.shop.dto.OrderCancelResponseDTO;
import com.devh.project.shop.dto.OrderCreateRequestDTO;
import com.devh.project.shop.dto.OrderCreateResponseDTO;
import com.devh.project.shop.dto.OrderSearchRequestDTO;
import com.devh.project.shop.dto.OrderSearchResponseDTO;
import com.devh.project.shop.entity.Delivery;
import com.devh.project.shop.entity.Order;
import com.devh.project.shop.entity.OrderItem;
import com.devh.project.item.entity.Item;
import com.devh.project.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
//    private final ItemRepository itemRepository;

    public OrderCreateResponseDTO create(OrderCreateRequestDTO orderCreateRequestDTO) {
//        Member member = memberRepository.findById(orderCreateRequestDTO.getUserId()).orElseThrow();
//        Item item = itemRepository.findById(orderCreateRequestDTO.getItemId()).orElseThrow();
//
//        Delivery delivery = new Delivery(member.getAddress());
//        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderCreateRequestDTO.getCount());
//        Order order = orderRepository.save(
//                Order.createOrder(member, delivery, orderItem)
//        );
//        System.out.printf("%s의 주문 - %s [%d 원] %d개 \n배송지: %s", order.getMember().getUsername(), item.getName(), item.getPrice(), orderCreateRequestDTO.getCount(), order.getDelivery().getAddress().toString());
        return OrderCreateResponseDTO.builder()
                .orderId(/*order.getId()*/1L)
                .build();
    }

    public OrderCancelResponseDTO cancel(OrderCancelRequestDTO orderCancelRequestDTO) {
        boolean cancelResult = true;
        try {
            orderRepository.findById(orderCancelRequestDTO.getOrderId()).orElseThrow().cancel();
        } catch (Exception e) {
            e.printStackTrace();
            cancelResult = false;
        }
        return OrderCancelResponseDTO.builder()
                .result(cancelResult)
                .build();
    }

    public OrderSearchResponseDTO search(OrderSearchRequestDTO orderSearchRequestDTO) {
        return OrderSearchResponseDTO.builder()
                .orderList(orderRepository.findAll(orderSearchRequestDTO.toSpecification()))
                .build();
    }
}
