package com.grupo01.digitalbooking.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.grupo01.digitalbooking.domain.User;
import com.grupo01.digitalbooking.dto.UserDTO;
import com.grupo01.digitalbooking.repository.UserRepository;
import com.grupo01.digitalbooking.service.exceptions.ForbiddenException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
                .orElseThrow(()-> new NotFoundException("Nenhum usuário com este email foi encontrado"));
    }

    public String refreshAccessToken(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        if(cookies==null) throw new ForbiddenException("Erro ao atualizar cookie: nenhum cookie encontrado");
        Cookie refreshTokenCookie = null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("refresh_token")) refreshTokenCookie = cookie;
        }
        if (refreshTokenCookie==null) {
            log.error("Error refreshing access token: unable to find 'refresh_token' cookie");
            throw new ForbiddenException("Erro ao atualizar token de acesso: Não foi encontrado o cookie 'refresh_token'");
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
                    .withClaim("authorities",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .withIssuer("digitalbooking")
                    .withIssuedAt(new Date())
                    .withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                    .sign(algorithm);

        } catch (Exception e) {
            log.error("Error refreshing access token: {}", e.getMessage());
            throw new ForbiddenException(format("Erro ao atualizar token de acesso: Mensagem de erro: %s",e.getMessage()));
        }
    }

    public User findAuthenticatedUser(HttpServletRequest request,
                                      HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        log.info("Cookies: {}",request.getCookies());
        if(request.getCookies()!=null){
            for(Cookie cookie: request.getCookies()){
                log.info("Cookie: {}={}",cookie.getName(),cookie.getValue());
            }
        }
        log.info("Current User Name: {}",currentUserName);
        log.info("Credentials: {}",authentication.getCredentials());
        log.info("Principal: {}",authentication.getPrincipal());

        return userRepository.findByEmail(currentUserName).orElseThrow(() ->
                new NotFoundException("Usuário não encontrado"));
    }

    public List<ResponseCookie> createJwtCookies(String subject, List<String> authorities){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String access_token = JWT.create()
                .withSubject(subject)
                .withClaim("authorities",authorities)
                .withIssuer("digitalbooking")
                .withIssuedAt(java.sql.Date.from(Instant.now()))
                .withExpiresAt(java.sql.Date.valueOf(LocalDateTime.now().plusDays(1).toLocalDate()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(subject)
                .withClaim("authorities",authorities)
                .withIssuer("digitalbooking")
                .withIssuedAt(java.sql.Date.from(Instant.now()))
                .withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusWeeks(1)))
                .sign(algorithm);

        ResponseCookie accessTokenCookie = ResponseCookie.from("access_token",access_token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .domain("ctdprojetos.com.br")
                .maxAge(24*60*60)
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token",refresh_token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .domain("ctdprojetos.com.br")
                .maxAge(24*60*60)
                .build();
        return List.of(accessTokenCookie,refreshTokenCookie);
    }

    public UserDTO validateUser(HttpServletRequest request,
                                HttpServletResponse response) {

        return new UserDTO(findAuthenticatedUser(request,response));

    }
}
