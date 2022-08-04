package com.devh.project.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import static com.devh.project.shop.entity.OrderSpec.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private Order.Status status;

    public Specification<Order> toSpecification() {
        return where(
                usernameLike(memberName)
                        .and(orderStatusEquals(status))
        );
    }
}
