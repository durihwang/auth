package com.gabia.auth.aop;

import com.gabia.auth.service.CacheService;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Aspect
public class OAuth2CacheAop {

    private final CacheManager redisCacheManager;
    private final CacheService cacheService;

    @Autowired
    public OAuth2CacheAop(CacheManager redisCacheManager, CacheService cacheService) {
        this.redisCacheManager = redisCacheManager;
        this.cacheService = cacheService;
    }

    @Around("execution(* org.springframework.security.oauth2.provider.client.JdbcClientDetailsService.loadClientByClientId(..))")
    public Object loadClientByClientId(ProceedingJoinPoint pjp) throws Throwable {

        Object proceed = pjp.proceed();
        cacheService.save(proceed, "client");
//        findCache("client");
        return proceed;
    }

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object postAccessToken(ProceedingJoinPoint pjp) throws Throwable {
        ResponseEntity proceed = (ResponseEntity) pjp.proceed();
        cacheService.save(proceed, "token");
        return proceed;
    }

    @Around("execution(* org.springframework.security.oauth2.provider.token.TokenStore.readAccessToken(..))")
    public Object readAccessToken(ProceedingJoinPoint pjp) throws Throwable {

        Object proceed = pjp.proceed();
        /*Object token = findCache("token");
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken((String) token);*/
        return proceed;
    }

    private Object findCache(String name) throws Throwable {

        Cache cache = redisCacheManager.getCache("oauth");
        Object getCache = cache.get(name).get();
        System.out.println("getCache = " + getCache);

        return getCache;
    }
}
