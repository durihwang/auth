package com.gabia.auth.entity;

import com.gabia.auth.dto.ClientType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
public class ClientEntity implements ClientDetails {

    private String name;
    private String clientId;
    private ClientType clientType;
    private String clientSecret;
    private String redirectUri;
    private int accessTokenValidity;
    private Set<String> scope = new HashSet<>();
    private Map<String, Object> additionalInformation = new HashMap<>();
    private Set<String> grantTypes = new HashSet<>();

    public void addScope(String scope) {
        this.scope.add(scope);
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return null;
    }

    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        return Collections.unmodifiableSet(scope);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        grantTypes.add("authorization_code");
        grantTypes.add("refresh_token");
        grantTypes.add("client_credentials");
        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return null;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        additionalInformation.put("name", getName());
        additionalInformation.put("client_type", clientType.name());
        return additionalInformation;
    }
}
