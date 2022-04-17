package com.grupo01.digitalbooking.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsAuthenticationRequest {

    private String email;
    private String password;


}
