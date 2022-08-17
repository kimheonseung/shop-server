package com.devh.project.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.devh.project.common.exception.DuplicateEmailException;
import com.devh.project.common.exception.PasswordException;
import com.devh.project.common.exception.SignUpException;

@Aspect
@Component
public class SignUpAspect {
    @Pointcut("execution(public * com.devh.project.security.signup.service.*.*(..))")
    private void publicSignUpService() { }

    @Around("publicSignUpService()")
    public Object servicePerformanceAdvice(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (DuplicateEmailException | PasswordException e) {
            throw e;
        } catch (Exception e) {
            throw new SignUpException(e.getMessage());
        }
    }
}
