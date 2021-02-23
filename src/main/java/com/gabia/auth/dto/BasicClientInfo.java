package com.gabia.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BasicClientInfo {

    private String name;
    private String redirectUri;
    private String clientType;
}
