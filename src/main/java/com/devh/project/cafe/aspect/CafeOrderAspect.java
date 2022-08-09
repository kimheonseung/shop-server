package com.devh.project.cafe.aspect;

import com.devh.project.cafe.exception.CafeOrderServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CafeOrderAspect {
    @Pointcut("execution(public * com.devh.project.cafe.service.OrderService.*(..))")
    private void publicCafeOrderService() { }

    @Around("publicCafeOrderService()")
    public Object servicePerformanceAdvice(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Exception e) {
            throw new CafeOrderServiceException(e.getMessage());
        }
    }
}
