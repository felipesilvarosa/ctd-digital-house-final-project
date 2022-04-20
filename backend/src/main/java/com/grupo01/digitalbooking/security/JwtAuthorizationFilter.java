package com.grupo01.digitalbooking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.FAILED;
import static java.util.Arrays.stream;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if(path.equals("/login")||path.equals("/login/refreshToken")){
            try {
                filterChain.doFilter(request, response);
            }catch (Exception ex){
                log.error("Something happened: {}:{} \nCause: {}",ex.getClass(),ex.getMessage(),ex.getCause());
            }
        } else{
            Cookie[] requestCookies = request.getCookies();
            if(requestCookies==null){
                log.error("Error logging in: no cookie");
                response.setStatus(403);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        new DefaultResponseDTO(FAILED,"Nenhum cookie encontrado"));
                return;
            }
            String token = null;
            for (Cookie cookie : requestCookies) {
                if(cookie.getName().equals("access_token")) token = cookie.getValue();
            }
            if (token==null){
                log.error("Error logging in: no access cookie");
                response.setStatus(403);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        new DefaultResponseDTO(FAILED,"Cookie de acesso não encontrado"));
                return;
            }
            try{
                Algorithm algorithm = Algorithm.HMAC256(secret);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("authorities").asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                stream(roles).forEach(SimpleGrantedAuthority::new);
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(username,null,authorities));
                log.info("Logged user: {}",SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }catch (Exception e){
                log.error("Error logging in: {}",e.getMessage());
                response.setStatus(403);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        new DefaultResponseDTO(FAILED,e.getMessage()));
                return;
            }
            filterChain.doFilter(request,response);
        }
    }
}