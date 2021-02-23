package com.gabia.auth.service;

import com.gabia.auth.dto.BasicClientInfo;
import com.gabia.auth.dto.ClientType;
import com.gabia.auth.entity.ClientEntity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRegistrationService clientRegistrationService;

    @Override
    public List<ClientDetails> find() throws Exception {
        return clientRegistrationService.listClientDetails();
    }

    @Override
    public void save(BasicClientInfo basicClientInfo) throws Exception {

        ClientEntity app = new ClientEntity();
        app.setName(basicClientInfo.getName());
        app.setClientType(ClientType.valueOf(basicClientInfo.getClientType()));
        app.setClientId(UUID.randomUUID().toString());
        app.setClientSecret("{noop}"+UUID.randomUUID().toString());
        app.setAccessTokenValidity(3000);
        app.addScope("read_profile");
        app.addScope("read_contacts");

        clientRegistrationService.addClientDetails(app);
    }

    @Override
    public void remove(String clientId) throws Exception {
        clientRegistrationService.removeClientDetails(clientId);
    }

}
