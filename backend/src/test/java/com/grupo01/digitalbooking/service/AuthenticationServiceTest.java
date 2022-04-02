package com.grupo01.digitalbooking.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.grupo01.digitalbooking.domain.User;
import com.grupo01.digitalbooking.repository.UserRepository;
import com.grupo01.digitalbooking.service.exceptions.ForbiddenException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private HttpServletRequest request;

    private String existingString;
    private String nonExistingString;

    @BeforeEach
    void init(){
        this.existingString = "Existing String";
        this.nonExistingString = "Non Existing String";
        when(userRepository.findByEmail(existingString)).thenReturn(Optional.of(new User()));
        when(userRepository.findByEmail(nonExistingString)).thenReturn(Optional.empty());
    }

    @AfterAll
    static void clearMocks() {
        Mockito.framework().clearInlineMocks();
    }

    @Test
    void loadUserByUsernameShouldThrowExceptionWhenInvalidEmail(){
        assertThrows(NotFoundException.class,()->service.loadUserByUsername(nonExistingString));
    }

    @Test
    void loadUserByUsernameShouldReturnUserDetailsWhenValidEmail(){
        UserDetails testOutput = service.loadUserByUsername(existingString);
        assertNotNull(testOutput);
    }


    @Test
    void refreshAccessTokenShouldThrowExceptionWhenInvalidCookie(){
        Exception testOutput;
        when(request.getCookies()).thenReturn(null);
        testOutput = assertThrows(ForbiddenException.class,
                ()->service.refreshAccessToken(request));
        assertEquals("Error refreshing access token: no cookies were found", testOutput.getMessage());
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("not_refresh_token","fail")});
        testOutput = assertThrows(ForbiddenException.class,
                ()->service.refreshAccessToken(request));
        assertEquals("Error refreshing access token: unable to find 'refresh_token' cookie", testOutput.getMessage());
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("refresh_token","invalid")});
        assertThrows(ForbiddenException.class,
                ()->service.refreshAccessToken(request));
    }

    @Test
    void refreshAccessTokenShouldReturnNewTokenWhenValidCookie(){
        ReflectionTestUtils.setField(service,"secret","secret");
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String validToken = JWT.create().withSubject(existingString).sign(algorithm);
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("refresh_token",validToken)});
        String testOutput = service.refreshAccessToken(request);
        assertNotNull(testOutput);
        assertFalse(testOutput.isBlank());

    }

    @Test
    void findAuthenticatedUserShouldThrowExceptionWhenInvalidEmail(){
        SecurityContextHolder
                .getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(nonExistingString,null));
        assertThrows(NotFoundException.class,()->service.findAuthenticatedUser());
    }

    @Test
    void findAuthenticatedUserShouldReturnUserWhenValidEmail(){
        SecurityContextHolder
                .getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(existingString,null));
        User testOutput = service.findAuthenticatedUser();
        assertNotNull(testOutput);
    }

}