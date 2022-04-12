package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.Location;
import com.grupo01.digitalbooking.dto.LocationDTO;
import com.grupo01.digitalbooking.repository.CityRepository;
import com.grupo01.digitalbooking.service.exceptions.ConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @InjectMocks
    private CityService cityService;
    @Mock
    private CityRepository cityRepository;



    @Test
    void createShouldCreateNewCity(){
        LocationDTO locationDTO = mockCityDTO();
        cityService.createCity(locationDTO);
        verify(cityRepository, Mockito.times(1)).save(any());

    }

    @Test
    void createShouldFailWhenCityAlreadyExists(){
        LocationDTO locationDTO = mockCityDTO();
        Location location = new Location(locationDTO);

        when(cityRepository.findByName(locationDTO.getName().toLowerCase())).thenReturn(location);

        ConflictException e = assertThrows(ConflictException.class,
                ()-> cityService.createCity(locationDTO));

        assertTrue(e.getMessage().contains("Cidade " + locationDTO.getName() + " já cadastrada"));

    }

    @Test

    void getCityShouldReturnListOfCityDTO(){
        when(cityRepository.findAll()).thenReturn(List.of());
        List<LocationDTO> testOutput = cityService.getCity();
        assertNotNull(testOutput);
    }


    public LocationDTO mockCityDTO(){
        return LocationDTO.builder()
                .country("Brasil")
                .name("São Paulo")
                .build();
    }


}