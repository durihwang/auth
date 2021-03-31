package com.gabia.auth.aop;

import com.gabia.auth.service.CacheService;
import lombok.val;
import net.minidev.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Optional;

@Component
@Aspect
public class OAuth2CacheAop {

    private final CacheManager redisCacheManager;
    private final CacheService cacheService;
    private static final int MAX_RETRIES = 4;

    @Autowired
    public OAuth2CacheAop(CacheManager redisCacheManager, CacheService cacheService) {
        this.redisCacheManager = redisCacheManager;
        this.cacheService = cacheService;
    }

    @Around("execution(* org.springframework.security.oauth2.provider.client.JdbcClientDetailsService.loadClientByClientId(..))")
    public Object loadClientByClientId(ProceedingJoinPoint pjp) throws Throwable {

        Object client = findCache("client");
        if (client instanceof Object) {
            return client;
        } else {
            Object proceed = pjp.proceed();
            cacheService.save(proceed, "client");
            System.out.println("no Cache");
            return proceed;
        }
    }

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.*(..))")
    public Object execute (ProceedingJoinPoint pjp) throws Throwable {
        int tts = 1000;
        for (int i=0; i<MAX_RETRIES; i++) {
            try {
                return pjp.proceed();
            } catch (DuplicateKeyException e) {
                Thread.sleep(tts);
                tts = tts*2;
            }
        }
        throw new IllegalStateException("Could not execute: " + pjp.getSignature().getName());
    }

    /*@Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object postAccessToken(ProceedingJoinPoint pjp) throws Throwable {
        ResponseEntity proceed = (ResponseEntity) pjp.proceed();
        cacheService.save(proceed, "token");
        return proceed;
    }

    @Around("execution(* org.springframework.security.oauth2.provider.token.store.JdbcTokenStore.readAccessToken(..))")
    public Object readAccessToken(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println("request = " + getHeaders(request));

        Object proceed = pjp.proceed();
//        System.out.println("proceed = " + proceed);
        *//*Object token = findCache("token");
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken((String) token);*//*
        return proceed;
    }

    @Around("execution(* org.springframework.jdbc.core.JdbcTemplate.queryForObject(..))")
    public Object queryForObject(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = pjp.proceed();
        System.out.println("proceed = " + proceed);
        return proceed;
    }

    private static JSONObject getHeaders(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String param = headers.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, request.getHeader(param));
        }
        return jsonObject;
    }*/

    private Object findCache(String name) {

        Optional<CacheManager> cache = Optional.ofNullable(redisCacheManager);
        Object oauth = cache.orElse(null).getCache("oauth").get(name);

        if (oauth instanceof Object) {
            return ((Cache.ValueWrapper) oauth).get();
        } else {
            return null;
        }
    }
}
