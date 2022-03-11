package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.CategoryDTO;
import com.grupo01.digitalbooking.dto.CitiesDTO;
import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.service.CitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.SUCCESS;

@RestController
@RequestMapping("cities")
@RequiredArgsConstructor
public class CitiesController {

    private final CitiesService citiesService;


    @GetMapping()
    public ResponseEntity<DefaultResponseDTO> findAllCities() {
        List<CitiesDTO> response = citiesService.getCities();
        Map<String, List<CitiesDTO>> data = Map.of("cities", response);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, data, "Cities retrieved successfully"));
    }

    @PostMapping("/new")
    public ResponseEntity<DefaultResponseDTO> createCategory(@RequestBody CitiesDTO citiesDTO) {
        CitiesDTO response = citiesService.createCity(citiesDTO);
        Map<String, CitiesDTO> data = Map.of("cities", response);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, data, "City created successfully"));
    }

}
