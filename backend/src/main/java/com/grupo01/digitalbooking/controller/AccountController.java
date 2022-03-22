package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.NewUserDTO;
import com.grupo01.digitalbooking.dto.UserDTO;
import com.grupo01.digitalbooking.service.SignupService;
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
public class AccountController {

    private final SignupService service;

    @PostMapping
    public ResponseEntity<UserDTO> createNewUser(@RequestBody NewUserDTO newUser){
        UserDTO result = service.createNewUser(newUser);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
