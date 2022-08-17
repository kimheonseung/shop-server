package com.devh.project.security.token.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponseDTO;
import com.devh.project.security.token.dto.TokenGenerateRequestDTO;
import com.devh.project.security.token.dto.TokenGenerateResponseDTO;
import com.devh.project.security.token.dto.TokenInvalidateRequestDTO;
import com.devh.project.security.token.dto.TokenInvalidateResponseDTO;
import com.devh.project.security.token.dto.TokenRefreshRequestDTO;
import com.devh.project.security.token.dto.TokenRefreshResponseDTO;
import com.devh.project.security.token.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
@Slf4j
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/generate")
	public ApiResponseDTO<TokenGenerateResponseDTO> generate(@Valid @RequestBody TokenGenerateRequestDTO tokenGenerateRequestDTO) throws Exception {
    	log.info("[POST] /token/generate ... "+tokenGenerateRequestDTO);
    	return ApiResponseDTO.success(ApiStatus.Success.OK, tokenService.generateToken(tokenGenerateRequestDTO));
	}

	@PostMapping("/invalidate")
	public ApiResponseDTO<TokenInvalidateResponseDTO> invalidate(@Valid @RequestBody TokenInvalidateRequestDTO tokenInvalidateRequestDTO, HttpServletRequest request) throws Exception {
		log.info("[POST] /token/invalidate ... "+tokenInvalidateRequestDTO);
		return ApiResponseDTO.success(ApiStatus.Success.OK, tokenService.invalidateToken(tokenInvalidateRequestDTO, request));
	}
	
	@PostMapping("/refresh")
	public ApiResponseDTO<TokenRefreshResponseDTO> refresh(@RequestBody TokenRefreshRequestDTO tokenRefreshRequestDTO) throws Exception {
		log.info("[POST] /token/generate ... "+tokenRefreshRequestDTO);
		return ApiResponseDTO.success(ApiStatus.Success.OK, tokenService.refreshToken(tokenRefreshRequestDTO));
	}
}
