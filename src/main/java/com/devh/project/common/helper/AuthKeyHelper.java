package com.devh.project.common.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthKeyHelper {
	@Value("${auth.key.size}")
	private int keySize;
	@Autowired
	private SecureRandomHelper secureRandomHelper;
	
	public String generateAuthKey() {
		StringBuffer sbAuthKey = new StringBuffer();
		while(sbAuthKey.length() < this.keySize) {
			sbAuthKey.append(this.secureRandomHelper.getRandomInteger(10));
		}
		return sbAuthKey.toString();
	}
}
