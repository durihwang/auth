package com.gabia.auth.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class OAuth2CacheAop {

    @Around("execution(* org.springframework.security.oauth2.provider.client.JdbcClientDetailsService.loadClientByClientId(..))")
    public Object testLog(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("AOP START!!!!!");
        Object retVal = pjp.proceed();
        System.out.println("AOP END!!!!!");
        return retVal;
    }
}
