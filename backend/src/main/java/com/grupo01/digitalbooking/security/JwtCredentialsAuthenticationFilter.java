package com.grupo01.digitalbooking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.FAILED;
import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.SUCCESS;

public class JwtCredentialsAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final String secret;
    private final AuthenticationManager authenticationManager;

    public JwtCredentialsAuthenticationFilter(String secret, AuthenticationManager authenticationManager) {
        this.secret = secret;
        this.authenticationManager = authenticationManager;
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

        Algorithm algorithm = Algorithm.HMAC256(secret);
        String access_token = JWT.create()
                .withSubject(authResult.getName())
                .withClaim("roles",authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withIssuer("srot")
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.valueOf(LocalDate.now().plusDays(1)))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(authResult.getName())
                .withClaim("authorities",authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withIssuer("srot")
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.valueOf(LocalDateTime.now().plusWeeks(2).toLocalDate()))
                .sign(algorithm);

        Cookie accessTokenCookie = new Cookie("access_token",refresh_token);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
//        accessTokenCookie.setDomain();//TODO this line should be uncommented and updated with actual domain on production
        Cookie refreshTokenCookie = new Cookie("refresh_token",refresh_token);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
//        refreshTokenCookie.setDomain(); //TODO this line should be uncommented and updated with actual domain on production
        response.addCookie(refreshTokenCookie);
        response.addCookie(accessTokenCookie);
        new ObjectMapper().writeValue(response.getOutputStream(),
                new DefaultResponseDTO(SUCCESS,"User authenticated successfully"));

    }

}
