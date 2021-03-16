package com.gabia.auth.repositories;

import org.springframework.http.ResponseEntity;

public interface CacheRepository {

    Object save(Object o, String name);

    Object save(ResponseEntity responseEntity, String name);
}
