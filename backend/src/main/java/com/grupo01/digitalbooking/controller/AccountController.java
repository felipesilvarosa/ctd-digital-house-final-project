package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.NewUserDTO;
import com.grupo01.digitalbooking.dto.UserDTO;
import com.grupo01.digitalbooking.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class AccountController {

    private final SignupService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> createNewUser(@RequestBody NewUserDTO newUser){
        UserDTO result = service.createNewUser(newUser);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
