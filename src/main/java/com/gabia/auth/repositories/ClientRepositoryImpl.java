package com.gabia.auth.repositories;

import com.gabia.auth.dto.BasicClientInfo;
import com.gabia.auth.dto.ClientType;
import com.gabia.auth.entity.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository{

    private final ClientRegistrationService clientRegistrationService;

    @Override
    public void save(BasicClientInfo basicClientInfo) throws Exception {

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(basicClientInfo.getName());
        clientEntity.setClientType(ClientType.valueOf(basicClientInfo.getClientType()));
        clientEntity.setClientId(UUID.randomUUID().toString());
        clientEntity.setClientSecret("{noop}"+UUID.randomUUID().toString());
        clientEntity.setAccessTokenValidity(3000);
        clientEntity.addScope("read_profile");
        clientEntity.addScope("read_contacts");

        clientRegistrationService.addClientDetails(clientEntity);
    }
}
