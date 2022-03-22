package com.grupo01.digitalbooking.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.grupo01.digitalbooking.domain.User;
import com.grupo01.digitalbooking.repository.UserRepository;
import com.grupo01.digitalbooking.service.exceptions.ForbiddenException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    @Value("${sdt.security.jwt.secret}")
    private String secret;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new NotFoundException("No user with provided information was found"));
    }

    public String refreshAccessToken(HttpServletRequest request, HttpServletResponse response){

        Cookie[] cookies = request.getCookies();
        if(cookies==null) throw new ForbiddenException("Error refreshing access token: no cookies were found");
        Cookie refreshTokenCookie = null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("refresh_token")) refreshTokenCookie = cookie;
        }
        if (refreshTokenCookie==null) {
            log.error("Error refreshing access token: unable to find 'refresh_token' cookie");
            throw new ForbiddenException("Error refreshing access token: unable to find 'refresh_token' cookie");
        }
        try {
            String refreshToken = refreshTokenCookie.getValue();
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            String username = decodedJWT.getSubject();
            User user = (User) loadUserByUsername(username);
            return JWT.create()
                    .withSubject(user.getEmail())
                    .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .withIssuer("srot")
                    .withIssuedAt(new Date())
                    .withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                    .sign(algorithm);

        } catch (Exception e) {
            log.error("Error refreshing access token: {}", e.getMessage());
            throw new ForbiddenException(format("Error refreshing access token: %s",e.getMessage()));
        }
    }

    public User findAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return userRepository.findByEmail(currentUserName).orElseThrow(() ->
                new NotFoundException("User " + currentUserName + " not found"));
    }
}
