package com.devh.project.cafe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CafeMenuCreateRequestDTO {
    private List<CafeMenuDTO> cafeMenuList;
}
