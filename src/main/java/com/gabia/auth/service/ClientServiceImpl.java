package com.gabia.auth.service;

import com.gabia.auth.dto.BasicClientInfo;
import com.gabia.auth.entity.ClientEntity;
import com.gabia.auth.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRegistrationService clientRegistrationService;
    private final ClientRepository clientRepository;

    @Override
    public List<ClientDetails> find() throws Exception {
        return clientRegistrationService.listClientDetails();
    }

    @Override
    public void save(BasicClientInfo basicClientInfo) throws Exception {
        clientRepository.save(basicClientInfo);
    }

    @Override
    public void remove(String clientId) throws Exception {
        clientRegistrationService.removeClientDetails(clientId);
    }

}
