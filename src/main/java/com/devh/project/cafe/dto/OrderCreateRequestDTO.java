package com.devh.project.cafe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderCreateRequestDTO {
    private Long memberId;
    private List<Long> menuIdList;
    private List<Integer> countList;
}
