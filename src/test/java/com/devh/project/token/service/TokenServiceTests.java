package com.devh.project.token.service;

import com.devh.project.common.entity.Member;
import com.devh.project.common.entity.MemberToken;
import com.devh.project.common.helper.AES256Helper;
import com.devh.project.common.helper.BCryptHelper;
import com.devh.project.common.helper.JwtHelper;
import com.devh.project.common.repository.MemberRepository;
import com.devh.project.common.repository.MemberTokenRepository;
import com.devh.project.token.constant.TokenStatus;
import com.devh.project.token.exception.TokenGenerateException;
import com.devh.project.token.exception.TokenInvalidateException;
import com.devh.project.token.model.Token;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@Transactional
public class TokenServiceTests {
    @Mock
    private AES256Helper aes256Helper;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberTokenRepository memberTokenRepository;
    @Mock
    private JwtHelper jwtHelper;
    @Mock
    private BCryptHelper bcryptHelper;
    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    public void beforeEach() {
        ReflectionTestUtils.setField(jwtHelper, "issuer", "devh");
        ReflectionTestUtils.setField(jwtHelper, "secretKey", "hkey");
        ReflectionTestUtils.setField(jwtHelper, "header", "Authorization");
        ReflectionTestUtils.setField(jwtHelper, "accessExpire", 10);
        ReflectionTestUtils.setField(jwtHelper, "refreshExpire", 10);
    }


    @Nested
    @DisplayName("Generate")
    class GenerateToken {
        @Test
        @DisplayName("성공")
        public void success() throws Exception {
            // given
            final String givenUsername = "testUser";
            final String givenPassword = "aes256Encrypted";
            final Member givenMember = Member.builder()
                    .username(givenUsername)
                    .password("bcryptEncoded")
                    .build();
            final Token givenToken = Token.builder()
                    .tokenStatus(TokenStatus.LOGIN_SUCCESS)
                    .accessToken("accessToken")
                    .refreshToken("refreshToken")
                    .build();
            given(memberRepository.findByUsername(givenUsername)).willReturn(Optional.of(givenMember));
            given(aes256Helper.decrypt(givenPassword)).willReturn("rawPassword");
            given(bcryptHelper.matches("rawPassword", givenMember.getPassword())).willReturn(true);
            given(jwtHelper.generateTokenByUsername(givenUsername)).willReturn(givenToken);
            // when
            Token token = tokenService.generateToken(givenUsername, givenPassword);
            // then
            assertEquals(token.getTokenStatus(), givenToken.getTokenStatus());
            assertEquals(token.getAccessToken(), givenToken.getAccessToken());
            assertEquals(token.getRefreshToken(), givenToken.getRefreshToken());
        }

        @Test
        @DisplayName("사용자가 존재하지 않음")
        public void userNotFound() {
            // given
            final String givenUsername = "testUser";
            given(memberRepository.findByUsername(givenUsername)).willThrow(TokenGenerateException.class);
            // then
            assertThrows(TokenGenerateException.class, () -> tokenService.generateToken(givenUsername, "anyPassword"), givenUsername + " does not exists.");
        }

        @Test
        @DisplayName("비밀번호 오류")
        public void passwordNotMatch() throws Exception {
            // given
            final String givenUsername = "testUser";
            final String givenPassword = "aes256Encrypted";
            final Member givenMember = Member.builder()
                    .username(givenUsername)
                    .password("bcryptEncrypted")
                    .build();
            given(memberRepository.findByUsername(givenUsername)).willReturn(Optional.of(givenMember));
            given(aes256Helper.decrypt(givenPassword)).willReturn("rawPassword");
            given(bcryptHelper.matches("rawPassword", "bcryptEncrypted")).willReturn(false);
            // then
            assertThrows(TokenGenerateException.class, () -> tokenService.generateToken(givenUsername, "aes256Encrypted"), "password not matches");
        }
    }

    @Nested
    @DisplayName("Invalidate")
    class InvalidateToken {
        @Test
        @DisplayName("성공")
        public void success() throws Exception {
            // given
            final String givenUsername = "testUser";
            final MockHttpServletRequest givenServletRequest = new MockHttpServletRequest();
            givenServletRequest.addHeader("Authorization", "accessToken");
            final Member givenMember = Member.builder()
                    .username(givenUsername)
                    .build();
            given(jwtHelper.getUsernameFromRequest(givenServletRequest)).willReturn(givenUsername);
            given(memberRepository.findByUsername(givenUsername)).willReturn(Optional.of(givenMember));
            // when
            boolean isInvalidate = tokenService.invalidateToken(givenUsername, givenServletRequest);
            // then
            assertTrue(isInvalidate);
        }

        @Test
        @DisplayName("JWT 오류")
        public void jwtError() throws Exception {
            // given
            final String givenUsername = "testUser";
            final MockHttpServletRequest givenServletRequest = new MockHttpServletRequest();
            givenServletRequest.addHeader("wrongHeader", "accessToken");
            given(jwtHelper.getUsernameFromRequest(givenServletRequest)).willThrow(JwtException.class);
            // then
            assertThrows(JwtException.class, () -> tokenService.invalidateToken(givenUsername, givenServletRequest));
        }

        @Test
        @DisplayName("username 오류")
        public void usernameError() throws Exception {
            // given
            final String givenUsername = "testUser";
            final MockHttpServletRequest givenServletRequest = new MockHttpServletRequest();
            givenServletRequest.addHeader("Authorization", "accessToken");
            given(jwtHelper.getUsernameFromRequest(givenServletRequest)).willReturn("wrongUsername");
            // then
            assertThrows(TokenInvalidateException.class, () -> tokenService.invalidateToken(givenUsername, givenServletRequest));
        }
    }

    @Nested
    @DisplayName("Refresh")
    class RefreshToken {
        @Test
        public void refreshToken() throws Exception {
            // given
            final String givenUsername = "testUser";
            final Member givenMember = Member.builder()
                    .username(givenUsername)
                    .build();
            final String givenBeforeAccessToken = "beforeRefresh_accessToken";
            final String givenBeforeRefreshToken = "beforeRefresh_refreshToken";
            final Token givenToken = Token.builder()
                    .tokenStatus(TokenStatus.LOGIN_SUCCESS)
                    .accessToken("beforeRefresh_accessToken")
                    .refreshToken("beforeRefresh_refreshToken")
                    .build();
            final MemberToken givenMemberToken = MemberToken.builder()
                    .member(givenMember)
                    .refreshToken(givenBeforeRefreshToken)
                    .build();
            final String givenAfterAccessToken = "afterRefresh_accessToken";
            final String givenAfterRefreshToken = "afterRefresh_refreshToken";
            final Token refreshedToken = Token.builder()
                    .tokenStatus(TokenStatus.LOGIN_SUCCESS)
                    .accessToken(givenAfterAccessToken)
                    .refreshToken(givenAfterRefreshToken)
                    .build();
            given(jwtHelper.isTokenExpired(givenBeforeAccessToken)).willReturn(true);
            given(jwtHelper.getUsernameFromToken(givenBeforeAccessToken)).willReturn(givenUsername);
            given(jwtHelper.isTokenExpired(givenBeforeRefreshToken)).willReturn(false);
            given(memberRepository.findByUsername(givenUsername)).willReturn(Optional.of(givenMember));
            given(memberTokenRepository.findByMember(givenMember)).willReturn(Optional.of(givenMemberToken));
            given(jwtHelper.generateTokenByUsername(givenUsername)).willReturn(refreshedToken);
            // when
            Token token = tokenService.refreshToken(givenToken);
            // then
            assertEquals(token.getTokenStatus(), TokenStatus.REFRESH_SUCCESS);
            assertEquals(token.getAccessToken(), givenAfterAccessToken);
            assertEquals(token.getRefreshToken(), givenAfterRefreshToken);
        }

        @Test
        @DisplayName("Access 토큰 유효")
        public void accessTokenNotExpired() throws Exception {
            // given
            final String givenAccessToken = "expiredAccessToken";
            given(jwtHelper.isTokenExpired(givenAccessToken)).willReturn(false);
            // when
            Token token = tokenService.refreshToken(Token.builder().accessToken(givenAccessToken).build());
            // then
            assertEquals(token.getTokenStatus(), TokenStatus.ACCESS_TOKEN_NOT_EXPIRED);
        }

        @Test
        @DisplayName("Refresh 토큰이 만료됨")
        public void refreshTokenExpired() throws Exception {
            // given
            final String givenUsername = "testUser";
            final String givenAccessToken = "accessToken";
            final String givenRefreshToken = "expiredRefreshToken";
            given(jwtHelper.isTokenExpired(givenAccessToken)).willReturn(true);
            given(jwtHelper.getUsernameFromToken(givenAccessToken)).willReturn(givenUsername);
            given(jwtHelper.isTokenExpired(givenRefreshToken)).willReturn(true);
            // when
            Token token = tokenService.refreshToken(Token.builder().accessToken(givenAccessToken).refreshToken(givenRefreshToken).build());
            // then
            assertEquals(token.getTokenStatus(), TokenStatus.LOGIN_REQUIRED);
        }

        @Test
        @DisplayName("Refresh 토큰이 서로 다름")
        public void refreshTokenNotEquals() throws Exception {
            // given
            final String givenUsername = "testUser";
            final Member givenMember = Member.builder()
                    .username(givenUsername)
                    .build();
            final String givenAccessToken = "accessToken";
            final String givenRefreshToken = "expiredRefreshToken";
            final String givenRecordRefreshToken = "recordRefreshToken";
            final MemberToken givenMemberToken = MemberToken.builder()
                    .member(givenMember)
                    .refreshToken(givenRecordRefreshToken)
                    .build();
            given(jwtHelper.isTokenExpired(givenAccessToken)).willReturn(true);
            given(jwtHelper.getUsernameFromToken(givenAccessToken)).willReturn(givenUsername);
            given(jwtHelper.isTokenExpired(givenRefreshToken)).willReturn(false);
            given(memberRepository.findByUsername(givenUsername)).willReturn(Optional.of(givenMember));
            given(memberTokenRepository.findByMember(givenMember)).willReturn(Optional.of(givenMemberToken));
            // when
            Token token = tokenService.refreshToken(Token.builder().accessToken(givenAccessToken).refreshToken(givenRefreshToken).build());
            // then
            assertEquals(token.getTokenStatus(), TokenStatus.REFRESH_FAIL);
        }
    }
}