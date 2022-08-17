package com.devh.project.cafe.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.devh.project.cafe.constant.OrderStatus;
import com.devh.project.cafe.entity.Order;
import com.devh.project.common.entity.Member;

public class OrderSpecification {
	
	public static Specification<Order> usernameEquals(final String username) {
		return new Specification<Order>() {
			private static final long serialVersionUID = 6965833536455236946L;

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if(StringUtils.isEmpty(username))
					return null;
				Join<Order, Member> join = root.join("member", JoinType.INNER);
				return criteriaBuilder.equal(join.<String>get("username"), username);
			}
		};
	}
	
	public static Specification<Order> usernameLike(final String username) {
        return new Specification<Order>() {
			private static final long serialVersionUID = -9157494880521689986L;

			@Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(StringUtils.isEmpty(username))
                    return null;
                Join<Order, Member> join = root.join("member", JoinType.INNER);
                return criteriaBuilder.like(join.<String>get("username"), "%"+username+"%");
            }
        };
    }

    public static Specification<Order> orderStatusEquals(final OrderStatus status) {
        return new Specification<Order>() {
			private static final long serialVersionUID = -8622744837061743212L;

			@Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(status == null)
                    return null;
                return criteriaBuilder.equal(root.get("status"), status);
            }
        };
    }

}
