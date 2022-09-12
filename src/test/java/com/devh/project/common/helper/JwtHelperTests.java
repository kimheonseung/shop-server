package com.devh.project.common.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.devh.project.token.model.Token;
import com.devh.project.token.constant.TokenStatus;

import io.jsonwebtoken.ExpiredJwtException;

@ExtendWith(MockitoExtension.class)
public class JwtHelperTests {
	@InjectMocks
	JwtHelper jwtHelper;
	
	@BeforeEach
    public void beforeEach() throws NoSuchAlgorithmException {
        ReflectionTestUtils.setField(jwtHelper, "issuer", "devh");
        ReflectionTestUtils.setField(jwtHelper, "secretKey", "hkey");
        ReflectionTestUtils.setField(jwtHelper, "header", "Authorization");
        ReflectionTestUtils.setField(jwtHelper, "accessExpire", 10);
        ReflectionTestUtils.setField(jwtHelper, "refreshExpire", 10);
    }
	
	@Test
	public void generateTokenByUsername() {
		// given
		final String givenUsername = "heon";
		// when
		Token token = jwtHelper.generateTokenByUsername(givenUsername);
		System.out.println(token);
		// then
		assertEquals(token.getTokenStatus(), TokenStatus.LOGIN_SUCCESS);
	}
	
	@Test
	public void isTokenExpired() {
		// given
		final String givenToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZXZoIiwic3ViIjoiaGVvbiIsImlhdCI6MTY1OTY2NDQwMywiZXhwIjoxNjU5NjY0NDEzfQ.50jgaoUHKdWz4po9xoqte03BUBsiX8F3du6GahPRjyI";
		// then
		assertThrows(ExpiredJwtException.class, () -> jwtHelper.isTokenExpired(givenToken));
	}
	
	@Test
	public void validateToken() {
		// given
		final String givenToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZXZoIiwic3ViIjoiaGVvbiIsImlhdCI6MTY1OTY2NDQwMywiZXhwIjoxNjU5NjY0NDEzfQ.50jgaoUHKdWz4po9xoqte03BUBsiX8F3du6GahPRjyI";
		// then
		assertThrows(ExpiredJwtException.class, () -> jwtHelper.validateToken(givenToken));
	}
}
