package com.devh.project.shop.entity;

import com.devh.project.common.entity.Member;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class OrderSpec {
    public static Specification<Order> usernameEquals(final String username) {
        return new Specification<Order>() {
			private static final long serialVersionUID = 2061810494967884232L;

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
			private static final long serialVersionUID = -959666549489851020L;

			@Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(StringUtils.isEmpty(username))
                    return null;
                Join<Order, Member> join = root.join("member", JoinType.INNER);
                return criteriaBuilder.like(join.<String>get("username"), "%"+username+"%");
            }
        };
    }

    public static Specification<Order> orderStatusEquals(final Order.Status status) {
        return new Specification<Order>() {
			private static final long serialVersionUID = -6387963554330013198L;

			@Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(status == null)
                    return null;
                return criteriaBuilder.equal(root.get("status"), status);
            }
        };
    }
}
