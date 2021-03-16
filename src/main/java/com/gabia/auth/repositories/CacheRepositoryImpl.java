package com.gabia.auth.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;

public class CacheRepositoryImpl implements CacheRepository{

    @Override
    @Cacheable(value = "oauth", key = "#name")
    public Object save(Object o, String name) {
        return o;
    }

    @Override
    @Cacheable(value = "oauth", key = "#name")
    public Object save(ResponseEntity ResponseEntity, String name) {
        return ResponseEntity.getBody().toString();
    }

}
