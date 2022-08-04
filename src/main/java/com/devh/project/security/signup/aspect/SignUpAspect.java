package com.devh.project.security.signup.aspect;

import com.devh.project.security.signup.exception.DuplicateEmailException;
import com.devh.project.security.signup.exception.PasswordException;
import com.devh.project.security.signup.exception.SignUpException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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
