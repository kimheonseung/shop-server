package com.devh.project.item.aspect;

import com.devh.project.item.exception.ItemException;
import com.devh.project.item.exception.NotEnoughStockException;
import com.devh.project.item.exception.UnknownDiscriminatorException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ItemAspect {
    @Pointcut("execution(public * com.devh.project.item.service.*.*(..))")
    private void publicSignUpService() { }

    @Around("publicSignUpService()")
    public Object servicePerformanceAdvice(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (UnknownDiscriminatorException | NotEnoughStockException e) {
            throw e;
        } catch (Exception e) {
            throw new ItemException(e.getMessage());
        }
    }
}
