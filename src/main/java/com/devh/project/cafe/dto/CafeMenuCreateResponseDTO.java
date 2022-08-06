package com.devh.project.cafe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class CafeMenuCreateResponseDTO {
    private int count;
    private boolean result;
}
