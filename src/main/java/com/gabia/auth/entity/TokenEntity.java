package com.gabia.auth.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "oauth_client_details")
public class TokenEntity {

    @Id
    private String clientId;
    private String clientSecret;
}