package com.devh.project.common.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import com.devh.project.common.entity.RedisMember;

@DataRedisTest
public class RedisMemberRepositoryTests {
	@Autowired
	private RedisMemberRepository redisMemberRepository;
	
	@BeforeEach
	public void beforeEach() {
		redisMemberRepository.deleteById("devh");
		RedisMember defaultRedisMember = RedisMember.builder()
				.username("devh")
				.password("devh")
				.authKey("key")
				.build();
		redisMemberRepository.save(defaultRedisMember);
	}
	
	@Test
	public void save() {
		// given
		final String givenUsername = "devh";
		RedisMember redisMember = RedisMember.builder()
				.username(givenUsername)
				.password("devh")
				.authKey("authKey")
			.build();
		// when
		RedisMember m = redisMemberRepository.save(redisMember);
		// then
		assertEquals(m.getUsername(), redisMember.getUsername());
		assertEquals(m.getPassword(), redisMember.getPassword());
	}
	@Test
	public void findById() {
		// given
		final String givenUsername = "devh";
		// when
		RedisMember m = redisMemberRepository.findById(givenUsername).orElseThrow();
		// then
		assertEquals(m.getUsername(), givenUsername);
	}
	@Test
	public void deleteById() {
		
	}
}
