package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.Category;
import com.grupo01.digitalbooking.domain.Cities;
import com.grupo01.digitalbooking.dto.CategoryDTO;
import com.grupo01.digitalbooking.dto.CitiesDTO;
import com.grupo01.digitalbooking.repository.CitiesRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CitiesService {

    private final CitiesRepository citiesRepository;

    public List<CitiesDTO> getCities() {
        List<Cities> cities = citiesRepository.findAll();
        return cities.stream().map(CitiesDTO::new).toList();
    }

    public CitiesDTO createCity(CitiesDTO citiesDTO) {
        Cities city = new Cities(citiesDTO);

        Cities cityFound = citiesRepository.findByName(citiesDTO.getName().toLowerCase());

        if(Objects.nonNull(cityFound)){
            throw new ConflictException("Cidade " + citiesDTO.getName() + " já cadastrada");
        }

        citiesRepository.save(city);
        return new CitiesDTO(city);
    }
}
