package com.devh.project.token.service;

import com.devh.project.common.entity.Member;
import com.devh.project.common.entity.MemberToken;
import com.devh.project.common.helper.AES256Helper;
import com.devh.project.common.helper.BCryptHelper;
import com.devh.project.common.helper.JwtHelper;
import com.devh.project.common.repository.MemberRepository;
import com.devh.project.common.repository.MemberTokenRepository;
import com.devh.project.token.exception.TokenGenerateException;
import com.devh.project.token.exception.TokenInvalidateException;
import com.devh.project.token.exception.TokenNotFoundException;
import com.devh.project.token.model.Token;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

    private final AES256Helper aes256Helper;
    private final MemberRepository memberRepository;
    private final MemberTokenRepository memberTokenRepository;
    private final JwtHelper jwtHelper;
    private final BCryptHelper bcryptHelper;

	public Token generateToken(String username, String password) throws Exception {
		/* member check */
		Member member = memberRepository.findByUsername(username).orElseThrow(() -> new TokenGenerateException(username + " does not exists."));
		/* generate token */
		if(bcryptHelper.matches(aes256Helper.decrypt(password), member.getPassword())) {
			Token token = jwtHelper.generateTokenByUsername(username);
			/* check member token */
			MemberToken memberToken = memberTokenRepository.findByMember(member).orElse(MemberToken.builder()
					.member(member)
					.build());
			memberToken.setRefreshToken(token.getRefreshToken());
			memberTokenRepository.save(memberToken);
			return token;
		} else
			throw new TokenGenerateException("password not matches");
	}

	public boolean invalidateToken(String username, HttpServletRequest httpServletRequest) throws Exception {
		final String tokenUsername = jwtHelper.getUsernameFromRequest(httpServletRequest);
		if(StringUtils.equals(username, tokenUsername)) {
			Member member = memberRepository.findByUsername(username).orElseThrow(() -> new TokenInvalidateException(username+" not found."));
			memberTokenRepository.deleteByMember(member);
			return true;
		} else
			throw new TokenInvalidateException("token information is invalid.");
	}

	// jwtUtils 개선 필요
	public Token refreshToken(Token toBeRefreshedToken) throws Exception {
		final String accessToken = toBeRefreshedToken.getAccessToken();
		final String refreshToken = toBeRefreshedToken.getRefreshToken();

		if(jwtHelper.isTokenExpired(accessToken)) {
			String username;
			try {
				username = jwtHelper.getUsernameFromToken(accessToken);
			} catch (ExpiredJwtException e) {
				username = e.getClaims().getSubject();
			}

			try {
				if(jwtHelper.isTokenExpired(refreshToken)) {
					return Token.buildLoginRequired();
				} else {
					Member member = memberRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
					MemberToken memberToken = memberTokenRepository.findByMember(member).orElseThrow(TokenNotFoundException::new);
					final String recordRefreshToken = memberToken.getRefreshToken();
					if(StringUtils.equals(refreshToken, recordRefreshToken)) {
						Token refreshedToken = jwtHelper.generateTokenByUsername(username);
						memberToken.setRefreshToken(refreshedToken.getRefreshToken());
						memberTokenRepository.save(memberToken);
						return Token.buildRefreshSuccess(refreshedToken.getAccessToken(), refreshedToken.getRefreshToken());
					} else {
						return Token.buildRefreshFail();
					}
				}
			} catch (ExpiredJwtException | TokenNotFoundException e) {
				return Token.buildLoginRequired();
			} catch (NoSuchElementException e) {
				return Token.buildInvalid();
			}
		} else {
			return Token.buildAccessTokenNotExpired(accessToken, refreshToken);
		}
	}
    
}
