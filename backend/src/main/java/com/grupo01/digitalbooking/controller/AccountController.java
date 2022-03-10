package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.domain.User;
import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.dto.NewUserDTO;
import com.grupo01.digitalbooking.dto.UserDTO;
import com.grupo01.digitalbooking.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.SUCCESS;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class AccountController {

    private final SignupService service;

    @PostMapping()
    public ResponseEntity<DefaultResponseDTO> investorSignup(@RequestBody NewUserDTO newUser){
        UserDTO result = service.createNewUser(newUser);
        Map<String,UserDTO> data = Map.of("user",result);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS,data,"User created successfully"));
    }

}
