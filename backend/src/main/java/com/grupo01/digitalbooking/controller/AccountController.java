package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.NewUserDTO;
import com.grupo01.digitalbooking.dto.UserDTO;
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

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Api(value ="", tags = {"Contas"})
@Tag(name ="Contas", description="End point para controle de contas usuarios")
public class AccountController {

    private final SignupService service;

    @ApiOperation("Criação de nova conta de usuario")
    @PostMapping
    public ResponseEntity<UserDTO> createNewUser(@RequestBody NewUserDTO newUser){
        UserDTO result = service.createNewUser(newUser);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
