package com.grupo01.digitalbooking.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsAuthenticationRequest {

    private String username;
    private String password;


}
