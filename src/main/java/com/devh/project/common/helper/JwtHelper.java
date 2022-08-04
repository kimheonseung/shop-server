package com.devh.project.common.helper;

import com.devh.project.common.util.ExceptionUtils;
import com.devh.project.security.token.Token;
import com.devh.project.security.token.TokenStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtHelper {
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.header}")
    private String header;
    @Value("${jwt.expire.access}")
    private int accessExpire;
    @Value("${jwt.expire.refresh}")
    private int refreshExpire;

//    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /* default signature algorithm */
    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public String getUsernameFromToken(String token) throws ExpiredJwtException, MalformedJwtException {
        return getClaimsFromToken(token).getSubject();
    }

    public String getUsernameFromRequest(HttpServletRequest request) {
        final Claims claims = this.getClaimsFromToken(this.getTokenFromRequestHeader(request));
        return claims.getSubject();
    }

    public Token generateTokenByUsername(String username) {
        final Date now = new Date();
        final String accessToken = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(generateAccessExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
        final String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(generateRefreshExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
        return Token.builder()
                .tokenStatus(TokenStatus.LOGIN_SUCCESS)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validateToken(String token) {
        final String tokenUsername = this.getUsernameFromToken(token);
        return (
                tokenUsername != null && !isTokenExpired(token)
        );
    }

    public boolean isRefreshNecessary(String token) {
        final String tokenUsername = this.getUsernameFromToken(token);
        return (
                tokenUsername != null && isTokenExpired(token)
        );
    }

    public String getTokenFromRequestHeader(HttpServletRequest request) {
        return request.getHeader(header);
    }

    private Date generateAccessExpirationDate() {
        final long nowMillis = System.currentTimeMillis();
        return new Date(nowMillis + (accessExpire * 1000L));
    }

    private Date generateRefreshExpirationDate() {
        final long nowMillis = System.currentTimeMillis();
        return new Date(nowMillis + (refreshExpire * 1000L));
    }

    private Claims getClaimsFromToken(String token) throws ExpiredJwtException, MalformedJwtException {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | MalformedJwtException eje) {
            log.warn(ExceptionUtils.stackTraceToString(eje));
            throw eje;
        }
    }

    public boolean isTokenExpired(String token) throws ExpiredJwtException, MalformedJwtException {
        Date expireDate = getExpirationDate(token);
        return expireDate.before(new Date());
    }

    private Date getExpirationDate(String token) throws ExpiredJwtException, MalformedJwtException {
        final Claims claims = this.getClaimsFromToken(token);
        return claims.getExpiration();
    }
}
