package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.Location;
import com.grupo01.digitalbooking.dto.LocationDTO;
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

    public List<LocationDTO> getCity() {
        List<Location> cities = cityRepository.findAll();
        return cities.stream().map(LocationDTO::new).collect(Collectors.toList());
    }

    public LocationDTO createCity(LocationDTO locationDTO) {
        Location location = new Location(locationDTO);

        Location locationFound = cityRepository.findByName(locationDTO.getName().toLowerCase());

        if(Objects.nonNull(locationFound)){
            throw new ConflictException("Cidade " + locationDTO.getName() + " já cadastrada");
        }

        cityRepository.save(location);
        return new LocationDTO(location);
    }
}
