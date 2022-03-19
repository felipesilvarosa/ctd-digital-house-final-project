package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.CityDTO;
import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.SUCCESS;

@RestController
@RequestMapping("cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;


    @GetMapping()
    public ResponseEntity<DefaultResponseDTO> findAllCities() {
        List<CityDTO> response = cityService.getCity();
        Map<String, List<CityDTO>> data = Map.of("cities", response);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, data, "Cities retrieved successfully"));
    }

    @PostMapping("/new")
    public ResponseEntity<DefaultResponseDTO> createCategory(@RequestBody CityDTO cityDTO) {
        CityDTO response = cityService.createCity(cityDTO);
        Map<String, CityDTO> data = Map.of("cities", response);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, data, "City created successfully"));
    }

}
