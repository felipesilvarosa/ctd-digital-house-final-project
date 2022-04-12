package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.NewUserDTO;
import com.grupo01.digitalbooking.dto.UserDTO;
import com.grupo01.digitalbooking.service.AuthenticationService;
import com.grupo01.digitalbooking.service.SignupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Api(value ="", tags = {"Contas"})
@Tag(name ="Contas", description="End point para controle de contas usuarios")
public class AccountController {

    private final SignupService service;
    private final AuthenticationService authenticationService;


    @ApiOperation("Criação de nova conta de usuario")
    @PostMapping
    public ResponseEntity<UserDTO> createNewUser(@RequestBody NewUserDTO newUser,
                                                 HttpServletResponse response){
        UserDTO result = service.createNewUser(newUser);
        List<Cookie> cookies = authenticationService.createJwtCookies(result.getEmail(),List.of(result.getRole()));
        cookies.forEach(response::addCookie);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
