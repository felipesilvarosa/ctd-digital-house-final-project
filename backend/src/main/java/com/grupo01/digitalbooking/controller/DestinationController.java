package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.DestinationDTO;
import com.grupo01.digitalbooking.service.DestinationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("destinations")
@RequiredArgsConstructor
@Api(tags = {"Destinos"})
@Tag(name ="Cidades", description="End point para controle de cidades")
public class DestinationController {

    private final DestinationsService service;

    @ApiOperation("Busca todas as cidades")
    @GetMapping
    public ResponseEntity<List<DestinationDTO>> findAllDestinations() {
        List<DestinationDTO> response = service.getDestinations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Cria uma nova cidade")
    @PostMapping
    public ResponseEntity<DestinationDTO> createDestination(@RequestBody DestinationDTO destinationDTO) {
        DestinationDTO response = service.createDestination(destinationDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
