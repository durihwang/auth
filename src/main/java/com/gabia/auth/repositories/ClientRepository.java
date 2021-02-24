package com.gabia.auth.repositories;

import com.gabia.auth.dto.BasicClientInfo;

public interface ClientRepository {

    void save(BasicClientInfo basicClientInfo) throws Exception;
}
