package com.gabia.auth.repositories;

import com.gabia.auth.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TokenRepository extends JpaRepository<TokenEntity, String> {
    List<TokenEntity> findAllBy();
}
