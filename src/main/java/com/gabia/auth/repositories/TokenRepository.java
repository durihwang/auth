package com.gabia.auth.repositories;

import com.gabia.auth.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<TokenEntity, String> {
    List<TokenEntity> findAllBy();
}
