package com.gabia.auth.service;

import com.gabia.auth.repositories.CacheRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.ResponseEntity;

public class CacheServiceImpl implements CacheService{

    private final CacheRepository cacheRepository;

    public CacheServiceImpl(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    @Override
    public void save(Object o, String name) {
        cacheRepository.save(o, name);
    }

    @Override
    public void save(ResponseEntity responseEntity, String name) {
        cacheRepository.save(responseEntity, name);
    }

}
