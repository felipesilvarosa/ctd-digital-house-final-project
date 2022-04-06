package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.LocationDTO;
import com.grupo01.digitalbooking.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cities")
@RequiredArgsConstructor
@Api(value ="", tags = {"Cidades"})
@Tag(name ="Cidades", description="End point para controle de cidades")
public class CityController {

    private final CityService cityService;

    @ApiOperation("Busca todas as cidades")
    @GetMapping
    public ResponseEntity<List<LocationDTO>> findAllCities() {
        List<LocationDTO> response = cityService.getCity();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Cria uma nova cidade")
    @PostMapping
    public ResponseEntity<LocationDTO> createCity(@RequestBody LocationDTO locationDTO) {
        LocationDTO response = cityService.createCity(locationDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
