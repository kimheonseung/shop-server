package com.devh.project.common.helper;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class SecureRandomHelper {
	private final Random random;

	public SecureRandomHelper() throws NoSuchAlgorithmException {
		this.random = SecureRandom.getInstanceStrong();
	}
	
	// 0 <= rand < max
	public int getRandomInteger(int max) {
		return this.random.nextInt(max);
	}
}
