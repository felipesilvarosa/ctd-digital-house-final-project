package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.domain.User;
import com.grupo01.digitalbooking.dto.NewUserDTO;
import com.grupo01.digitalbooking.dto.UserDTO;
import com.grupo01.digitalbooking.service.AuthenticationService;
import com.grupo01.digitalbooking.service.SignupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Contas"})
@Tag(name ="Contas", description="End point para controle de contas usuarios")
@Slf4j
public class AccountController {

    private final SignupService signupService;
    private final AuthenticationService authenticationService;

    @ApiOperation("Criação de nova conta de usuario")
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createNewUser(@RequestBody NewUserDTO newUser){
        UserDTO result = signupService.createNewUser(newUser);
        List<ResponseCookie> cookies = authenticationService.createJwtCookies(result.getEmail(),List.of(result.getRole()));
        ResponseEntity<UserDTO> response = ResponseEntity.ok(result);
        cookies.forEach(cookie->response.getHeaders().add(SET_COOKIE,cookie.toString()));
        return response;
    }

    @GetMapping("users/validate")
    public ResponseEntity<UserDTO> validateUser(HttpServletRequest request,
                                                HttpServletResponse response){
        UserDTO result = authenticationService.validateUser(request,response);
        return new ResponseEntity<>(result, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login() throws IOException {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = (User) authenticationService.loadUserByUsername(username);
            List<ResponseCookie> jwtCookies =
                    authenticationService.createJwtCookies(user.getUsername(),
                    user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            UserDTO responseData = new UserDTO(user);
            ResponseEntity<UserDTO> response = ResponseEntity.status(OK)
                            .header(SET_COOKIE,jwtCookies.get(0).toString())
                            .header(SET_COOKIE,jwtCookies.get(1).toString())
                            .body(responseData);
            return response;
    }

}