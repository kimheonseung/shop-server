package com.devh.project.common.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptHelper {
	private final PasswordEncoder passwordEncoder;
	
	public String encode(String rawString) {
		return this.passwordEncoder.encode(rawString);
	}
	
	public boolean matches(String rawString, String encodedString) {
		return this.passwordEncoder.matches(rawString, encodedString);
	}
}
