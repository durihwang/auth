package com.gabia.auth.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.oauth2.client.test.OAuth2ContextConfiguration;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class OAuth2CacheAop {

    private final CacheManager redisCacheManager;

    public OAuth2CacheAop(CacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    @Around("execution(* org.springframework.security.oauth2.provider.client.JdbcClientDetailsService.loadClientByClientId(..))")
    public Object loadClientByClientId(ProceedingJoinPoint pjp) throws Throwable {

        return cache(pjp, "client");
    }

    /*@Around("execution(* org.springframework.security.oauth2.provider.token.TokenStore.readAccessToken(..))")
    @OAuth2ContextConfiguration
    public Object readAccessToken(ProceedingJoinPoint pjp) throws Throwable {

        return cache(pjp, "token");
    }

    @Around("execution(* org.springframework.security.oauth2.provider.token.TokenStore.readAuthentication())")
    public Object readAuthentication(ProceedingJoinPoint pjp) throws Throwable {

        return cache(pjp, "auth");
    }*/

    private Object cache(ProceedingJoinPoint pjp, String name) throws Throwable {

        Cache cache = redisCacheManager.getCache("oauth");
        Object client = cache.get(name);

        Object retVal;
        if (client instanceof Object) {
            retVal = cache.get(name).get();
        } else {
            retVal = pjp.proceed();
            cache.put(name, retVal);
        }

        return retVal;
    }
}
