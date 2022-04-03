package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.City;
import com.grupo01.digitalbooking.dto.CityDTO;
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
class CityServiceTest {

    @InjectMocks
    private CityService cityService;
    @Mock
    private CityRepository cityRepository;



    @Test
    void createShouldCreateNewCity(){
        CityDTO cityDTO = mockCityDTO();
        cityService.createCity(cityDTO);
        verify(cityRepository, Mockito.times(1)).save(any());

    }

    @Test
    void createShouldFailWhenCityAlreadyExists(){
        CityDTO cityDTO = mockCityDTO();
        City city = new City(cityDTO);

        when(cityRepository.findByName(cityDTO.getName().toLowerCase())).thenReturn(city);

        ConflictException e = assertThrows(ConflictException.class,
                ()-> cityService.createCity(cityDTO));

        assertTrue(e.getMessage().contains("Cidade " + cityDTO.getName() + " já cadastrada"));

    }

    @Test

    void getCityShouldReturnListOfCityDTO(){
        when(cityRepository.findAll()).thenReturn(List.of());
        List<CityDTO> testOutput = cityService.getCity();
        assertNotNull(testOutput);
    }


    public CityDTO mockCityDTO(){
        return CityDTO.builder()
                .country("Brasil")
                .name("São Paulo")
                .build();
    }


}