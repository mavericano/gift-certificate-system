package com.epam.esm.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.epam.esm.api.controller.GiftCertificateController.*(..))")
    public void logBeforeGiftCertificateControllerAdvice(JoinPoint joinPoint) {
        log.debug(joinPoint.getSignature().getName() + " method was called from GiftCertificateController");
    }

    @Before("execution(* com.epam.esm.api.controller.TagController.*(..))")
    public void logBeforeTagControllerAdvice(JoinPoint joinPoint) {
        log.debug(joinPoint.getSignature().getName() + " method was called from TagController");
    }

    @AfterReturning("execution(* com.epam.esm.api.exceptionhandler.GlobalExceptionHandler.*(..))")
    public void logAfterGlobalExceptionHandlerAdvice(JoinPoint joinPoint) {
        log.debug(joinPoint.getArgs()[0].toString());
        log.debug(joinPoint.getSignature().getName() + " method was called from GlobalExceptionHandler");
    }

    @AfterReturning("execution(* com.epam.esm.api.exceptionhandler.GlobalExceptionHandler.handleLeftoverException(..))")
    public void logAfterGlobalExceptionHandlerAdviceForLeftoverException(JoinPoint joinPoint) {
        log.error(joinPoint.getArgs()[0].toString() + " was not handled specifically, leftover");
        log.error(joinPoint.getSignature().getName() + " method was called from GlobalExceptionHandler");
    }
}
