package com.devh.project.security.token.aspect;

import com.devh.project.security.token.exception.TokenException;
import com.devh.project.security.token.exception.TokenGenerateException;
import com.devh.project.security.token.exception.TokenInvalidateException;
import com.devh.project.security.token.exception.TokenRefreshException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenAspect {
    @Pointcut("execution(public * com.devh.project.security.token.service.*.*(..))")
    private void publicTokenService() { }

    @Around("publicTokenService()")
    public Object servicePerformanceAdvice(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (TokenGenerateException | TokenInvalidateException | TokenRefreshException e) {
            throw e;
        } catch (Exception e) {
            throw new TokenException(e.getMessage());
        }
    }
}
