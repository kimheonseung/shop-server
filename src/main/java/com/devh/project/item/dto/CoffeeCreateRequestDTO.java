package com.devh.project.item.dto;

import com.devh.project.item.entity.Coffee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CoffeeCreateRequestDTO {
    private List<Coffee> itemList;
}
