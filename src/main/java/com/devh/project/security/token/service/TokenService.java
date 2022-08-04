package com.devh.project.security.token.service;

import com.devh.project.common.entity.User;
import com.devh.project.common.entity.UserToken;
import com.devh.project.common.helper.AES256Helper;
import com.devh.project.common.helper.BCryptHelper;
import com.devh.project.common.helper.JwtHelper;
import com.devh.project.common.repository.UserRepository;
import com.devh.project.common.repository.UserTokenRepository;
import com.devh.project.security.token.Token;
import com.devh.project.security.token.dto.TokenGenerateRequestDTO;
import com.devh.project.security.token.dto.TokenGenerateResponseDTO;
import com.devh.project.security.token.dto.TokenInvalidateRequestDTO;
import com.devh.project.security.token.dto.TokenInvalidateResponseDTO;
import com.devh.project.security.token.dto.TokenRefreshRequestDTO;
import com.devh.project.security.token.dto.TokenRefreshResponseDTO;
import com.devh.project.security.token.exception.TokenGenerateException;
import com.devh.project.security.token.exception.TokenInvalidateException;
import com.devh.project.security.token.exception.TokenNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TokenService {

    private final AES256Helper aes256Helper;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final JwtHelper jwtHelper;
    private final BCryptHelper bcryptHelper;

    public TokenGenerateResponseDTO generateToken(TokenGenerateRequestDTO tokenGenerateRequestDTO) throws Exception {
		final String username = tokenGenerateRequestDTO.getUsername();
		final String password = aes256Helper.decrypt(tokenGenerateRequestDTO.getPassword());
		/* member check */
		User user = userRepository.findByUsername(username).orElseThrow(() -> new TokenGenerateException(username + " does not exists."));
		/* generate token */
		if(bcryptHelper.matches(password, user.getPassword())) {
			Token token = jwtHelper.generateTokenByUsername(username);
			/* check member token */
			UserToken userToken = userTokenRepository.findByUser(user).orElse(UserToken.builder()
					.user(user)
					.build());
			userToken.setRefreshToken(token.getRefreshToken());
			userTokenRepository.save(userToken);
			return TokenGenerateResponseDTO.builder()
					.token(token)
					.build();
		} else
			throw new TokenGenerateException("password not matches");
	}

	public TokenInvalidateResponseDTO invalidateToken(TokenInvalidateRequestDTO tokenInvalidateRequestDTO, HttpServletRequest httpServletRequest) throws Exception {
		final String username = tokenInvalidateRequestDTO.getUsername();
		final String tokenUsername = jwtHelper.getUsernameFromRequest(httpServletRequest);
		if(StringUtils.equals(username, tokenUsername)) {
			User user = userRepository.findByUsername(username).orElseThrow(() -> new TokenInvalidateException(username+" not found."));
			userTokenRepository.deleteByUser(user);
			return TokenInvalidateResponseDTO.builder()
					.result(true)
					.build();
		} else
			throw new TokenInvalidateException("token information is invalid.");

	}

	// jwtUtils 개선 필요
	public TokenRefreshResponseDTO refreshToken(TokenRefreshRequestDTO tokenRefreshRequestDTO) throws Exception {
		final Token requestToken = tokenRefreshRequestDTO.getToken();
		final String accessToken = requestToken.getAccessToken();
		final String refreshToken = requestToken.getRefreshToken();
		final TokenRefreshResponseDTO tokenRefreshResponseDTO = TokenRefreshResponseDTO.builder().build();

		if(jwtHelper.isTokenExpired(accessToken)) {
			String username;
			try {
				username = jwtHelper.getUsernameFromToken(accessToken);
			} catch (ExpiredJwtException e) {
				username = e.getClaims().getSubject();
			}

			try {
				if(jwtHelper.isTokenExpired(refreshToken)) {
					tokenRefreshResponseDTO.setToken(Token.buildLoginRequired());
				} else {
					User user = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
					UserToken userToken = userTokenRepository.findByUser(user).orElseThrow(TokenNotFoundException::new);
					final String recordRefreshToken = userToken.getRefreshToken();
					if(StringUtils.equals(refreshToken, recordRefreshToken)) {
						Token refreshedToken = jwtHelper.generateTokenByUsername(username);
						tokenRefreshResponseDTO.setToken(Token.buildRefreshSuccess(refreshedToken.getAccessToken(), refreshedToken.getRefreshToken()));
						userToken.setRefreshToken(refreshedToken.getRefreshToken());
						userTokenRepository.save(userToken);
					} else {
						tokenRefreshResponseDTO.setToken(Token.buildRefreshFail());
					}
				}
			} catch (ExpiredJwtException | TokenNotFoundException e) {
				tokenRefreshResponseDTO.setToken(Token.buildLoginRequired());
			} catch (NoSuchElementException e) {
				tokenRefreshResponseDTO.setToken(Token.buildInvalid());
			}
		} else {
			tokenRefreshResponseDTO.setToken(Token.buildAccessTokenNotExpired(accessToken, refreshToken));
		}

		return tokenRefreshResponseDTO;
	}
    
}
