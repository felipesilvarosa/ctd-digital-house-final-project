package com.grupo01.digitalbooking.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.SUCCESS;

public class JwtCredentialsAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    public JwtCredentialsAuthenticationFilter(AuthenticationManager authenticationManager,AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            CredentialsAuthenticationRequest authenticationRequest =
                    new ObjectMapper().readValue(request.getInputStream(), CredentialsAuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        List<Cookie> jwtCookies = authenticationService.createJwtCookies(
                authResult.getPrincipal().toString(),
                authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        jwtCookies.forEach(response::addCookie);
        new ObjectMapper().writeValue(response.getOutputStream(),
                new DefaultResponseDTO(SUCCESS,"User authenticated successfully"));

    }

}
