package com.devh.project.token.controller;

import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponseDTO;
import com.devh.project.token.dto.TokenGenerateRequestDTO;
import com.devh.project.token.dto.TokenGenerateResponseDTO;
import com.devh.project.token.dto.TokenInvalidateRequestDTO;
import com.devh.project.token.dto.TokenInvalidateResponseDTO;
import com.devh.project.token.dto.TokenRefreshRequestDTO;
import com.devh.project.token.dto.TokenRefreshResponseDTO;
import com.devh.project.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
@Slf4j
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/generate")
	public ApiResponseDTO<TokenGenerateResponseDTO> generate(@Valid @RequestBody TokenGenerateRequestDTO tokenGenerateRequestDTO) throws Exception {
    	log.info("[POST] /token/generate ... "+tokenGenerateRequestDTO);
    	return ApiResponseDTO.success(ApiStatus.Success.OK,
				TokenGenerateResponseDTO.builder().
						token(tokenService.generateToken(tokenGenerateRequestDTO.getUsername(), tokenGenerateRequestDTO.getPassword()))
						.build());
	}

	@PostMapping("/invalidate")
	public ApiResponseDTO<TokenInvalidateResponseDTO> invalidate(@Valid @RequestBody TokenInvalidateRequestDTO tokenInvalidateRequestDTO, HttpServletRequest request) throws Exception {
		log.info("[POST] /token/invalidate ... "+tokenInvalidateRequestDTO);
		return ApiResponseDTO.success(ApiStatus.Success.OK,
				TokenInvalidateResponseDTO.builder()
					.result(tokenService.invalidateToken(tokenInvalidateRequestDTO.getUsername(), request))
					.build());
	}
	
	@PostMapping("/refresh")
	public ApiResponseDTO<TokenRefreshResponseDTO> refresh(@RequestBody TokenRefreshRequestDTO tokenRefreshRequestDTO) throws Exception {
		log.info("[POST] /token/generate ... "+tokenRefreshRequestDTO);
		return ApiResponseDTO.success(ApiStatus.Success.OK,
				TokenRefreshResponseDTO.builder()
						.token(tokenService.refreshToken(tokenRefreshRequestDTO.getToken()))
						.build());
	}
}
