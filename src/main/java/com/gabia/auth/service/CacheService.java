package com.gabia.auth.service;

import org.springframework.http.ResponseEntity;

public interface CacheService {

    void save(Object o, String name);

    void save(ResponseEntity responseEntity, String name);
}
