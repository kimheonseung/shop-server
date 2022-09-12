package com.devh.project.token.dto;

import javax.validation.constraints.NotBlank;

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
public class TokenGenerateRequestDTO {
//    @Email(message = "Not Valid Email")
	@NotBlank(message = "username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
}
