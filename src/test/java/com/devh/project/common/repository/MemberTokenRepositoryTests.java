package com.devh.project.common.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.devh.project.common.entity.Member;
import com.devh.project.common.entity.MemberToken;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
public class MemberTokenRepositoryTests {
	@Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberTokenRepository memberTokenRepository;

    @BeforeEach
    public void beforeEach() {
        Member member = Member.builder()
                .username("devh")
                .password(new BCryptPasswordEncoder().encode("devh"))
                .build();
        Member savedMember = memberRepository.save(member);
        memberTokenRepository.save(MemberToken.builder()
                .member(savedMember)
                .refreshToken("refreshToken")
            .build());
    }

    @Test
    public void findByMember() {
    	// given
        Member givenMember = memberRepository.findByUsername("devh").orElseThrow();
        // when
        MemberToken memberToken = memberTokenRepository.findByMember(givenMember).orElseThrow();
        // then
        assertEquals("refreshToken", memberToken.getRefreshToken());
    }
    
    @Test
    public void existsByMember() {
    	// given
    	Member givenMember = memberRepository.findByUsername("devh").orElseThrow();
    	// when
    	boolean exists = memberTokenRepository.existsByMember(givenMember);
    	// then
    	assertTrue(exists);
    }

    @Test
    public void deleteByMember() {
    	// given
        Member givenMember = memberRepository.findByUsername("devh").orElseThrow();
        // when
        memberTokenRepository.deleteByMember(givenMember);
        // then
        assertThrows(NoSuchElementException.class, () -> memberTokenRepository.findByMember(givenMember).orElseThrow(NoSuchElementException::new));
    }
}
