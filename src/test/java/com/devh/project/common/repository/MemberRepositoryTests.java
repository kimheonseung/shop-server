package com.devh.project.common.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.devh.project.common.entity.Member;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
public class MemberRepositoryTests {
	@Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository.save(Member.builder()
                .id(1L)
                .username("devh")
                .password("password")
            .build()
        );
    }

    @Nested
    @DisplayName("성공")
    class Success {
        @Test
        public void findByUsername() {
            // given
            final String givenUsername = "devh";
            // when
            Member member = memberRepository.findByUsername(givenUsername).orElseThrow();
            // then
            assertEquals(member.getUsername(), givenUsername);
        }
        @Test
        public void existsByUsername() {
            // given
            final String givenUsername = "devh";
            // when
            boolean exists = memberRepository.existsByUsername(givenUsername);
            // then
            assertTrue(exists);
        }
    }

    @Nested
    @DisplayName("실패")
    class Fail {
        @Test
        public void findByUsername() {
            // given
            final String givenUsername = "error";
            // then
            assertThrows(NoSuchElementException.class, () -> memberRepository.findByUsername(givenUsername).orElseThrow());
        }
        @Test
        public void existsByUsername() {
            // given
            final String givenUsername = "error";
            // when
            boolean exists = memberRepository.existsByUsername(givenUsername);
            // then
            assertFalse(exists);
        }
    }
}
