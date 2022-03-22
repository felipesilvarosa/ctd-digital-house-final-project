package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.CityDTO;
import com.grupo01.digitalbooking.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;


    @GetMapping
    public ResponseEntity<List<CityDTO>> findAllCities() {
        List<CityDTO> response = cityService.getCity();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CityDTO> createCategory(@RequestBody CityDTO cityDTO) {
        CityDTO response = cityService.createCity(cityDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
