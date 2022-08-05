package com.devh.project.common.helper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class BCryptHelperTests {
	@InjectMocks
	BCryptHelper bcryptHelper;
	
	@BeforeEach
    public void beforeEach() throws NoSuchAlgorithmException {
        ReflectionTestUtils.setField(bcryptHelper, "passwordEncoder", new BCryptPasswordEncoder());
    }
	
	@Test
	public void encode() {
		// given
		final String givenRawString = "test";
		// when
		String encodedString = this.bcryptHelper.encode(givenRawString);
		// then
		System.out.println(encodedString);
	}
	
	@Test
	public void matches() {
		// given
		final String givenRawString = "test";
		final String givenEncodedString = "$2a$10$XOzzm0y.T5QU6Reb6TUyUusBodpFNzcHJEYUZ0YikF3bF9h7ZMsdO";
		// when
		boolean matches = this.bcryptHelper.matches(givenRawString, givenEncodedString);
		// then
		assertTrue(matches);
	}

}