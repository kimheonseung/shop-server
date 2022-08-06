package com.devh.project.shop.dto;

import com.devh.project.shop.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static com.devh.project.shop.entity.OrderSpec.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchRequestDTO {
    private String memberName;
    private Order.Status status;
    public Specification<Order> toSpecification() {
        return where(
                usernameLike(memberName)
                        .and(orderStatusEquals(status))
        );
    }
}
