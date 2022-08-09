package com.devh.project.cafe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCreateRequestDTO {
    private Long memberId;
    private List<Long> menuIdList;
    private List<Integer> countList;
}
