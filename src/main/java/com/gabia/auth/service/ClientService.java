package com.gabia.auth.service;

import com.gabia.auth.dto.BasicClientInfo;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

    List<ClientDetails> find() throws Exception;

    void save(BasicClientInfo basicClientInfo) throws Exception;

    void remove(String clientId) throws Exception;
}
