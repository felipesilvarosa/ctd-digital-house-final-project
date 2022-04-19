package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.PolicyDTO;
import com.grupo01.digitalbooking.service.PolicyService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("policies")
@RequiredArgsConstructor
@Api(tags = {"Politicas"})
@Tag(name ="Politicas", description="End point para controle de politicas")
public class PolicyController {

    private final PolicyService service;

    @GetMapping
    public ResponseEntity<List<PolicyDTO>> getAllPolicies(){
        return ResponseEntity.ok(service.getAllPolicies());
    }

}
