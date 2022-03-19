package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.City;
import com.grupo01.digitalbooking.dto.CityDTO;
import com.grupo01.digitalbooking.repository.CityRepository;
import com.grupo01.digitalbooking.service.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<CityDTO> getCity() {
        List<City> cities = cityRepository.findAll();
        return cities.stream().map(CityDTO::new).collect(Collectors.toList());
    }

    public CityDTO createCity(CityDTO cityDTO) {
        City city = new City(cityDTO);

        City cityFound = cityRepository.findByName(cityDTO.getName().toLowerCase());

        if(Objects.nonNull(cityFound)){
            throw new ConflictException("Cidade " + cityDTO.getName() + " já cadastrada");
        }

        cityRepository.save(city);
        return new CityDTO(city);
    }
}
