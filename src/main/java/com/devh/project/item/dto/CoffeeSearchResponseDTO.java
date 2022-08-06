package com.devh.project.item.dto;

import com.devh.project.common.dto.PagingDTO;
import com.devh.project.item.entity.Coffee;
import com.devh.project.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeSearchResponseDTO {
    private List<Coffee> itemList;
    private PagingDTO paging;
}
