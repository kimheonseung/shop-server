package com.devh.project.cafe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCreateResponseDTO {
    private int count;
    private boolean result;
}
