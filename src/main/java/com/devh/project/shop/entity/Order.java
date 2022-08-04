package com.devh.project.shop.entity;

import com.devh.project.common.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ORDERS")
public class Order {
	
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;
    
    private Date orderDate;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    // 연관관계
    public void setUser(User user) {
    	this.user = user;
        user.getOrders().add(this);
    }
    
    public void addOrderItem(OrderItem orderItem) {
    	orderItems.add(orderItem);
    	orderItem.setOrder(this);
    }
    
    public void setDelivery(Delivery delivery) {
    	this.delivery = delivery;
    	delivery.setOrder(this);
    }

    //  생성
    public static Order createOrder(User user, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setUser(user);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(Status.ORDER);
        order.setOrderDate(new Date());
        return order;
    }

    // 취소
    public void cancel() {
        if(delivery.getStatus() == Delivery.Status.COMP) {
            throw new RuntimeException("이미 배성 완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(Status.CANCEL);
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 전체 주문 가격
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getOrderPrice();
        }
        return totalPrice;
    }

    public enum Status {
        ORDER, CANCEL;
    }
}
