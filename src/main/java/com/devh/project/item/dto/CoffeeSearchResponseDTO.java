package com.devh.project.item.dto;

import java.util.List;

import com.devh.project.common.dto.PagingDTO;
import com.devh.project.item.entity.Coffee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeSearchResponseDTO {
    private List<Coffee> itemList;
    private PagingDTO paging;
}
