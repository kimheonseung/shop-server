package com.devh.project.token.dto;

import com.devh.project.token.model.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenRefreshRequestDTO {
	private Token token;
}
