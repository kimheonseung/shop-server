package com.devh.project.security.token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenGenerateRequestDTO {
    @Email(message = "Not Valid Email")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
}
