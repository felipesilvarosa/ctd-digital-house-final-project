package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.Category;
import com.grupo01.digitalbooking.domain.Cities;
import com.grupo01.digitalbooking.dto.CategoryDTO;
import com.grupo01.digitalbooking.dto.CitiesDTO;
import com.grupo01.digitalbooking.repository.CitiesRepository;
import com.grupo01.digitalbooking.service.exceptions.ConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CitiesServiceTest {

    @InjectMocks
    private CitiesService citiesService;
    @Mock
    private CitiesRepository citiesRepository;



    @Test
    void createShouldCreateNewCity(){
        CitiesDTO cityDTO = mockCityDTO();
        citiesService.createCity(cityDTO);
        verify(citiesRepository, Mockito.times(1)).save(any());

    }

    @Test
    void createShouldFailWhenCityAlreadyExists(){
        CitiesDTO cityDTO = mockCityDTO();
        Cities city = new Cities(cityDTO);

        when(citiesRepository.findByName(cityDTO.getName().toLowerCase())).thenReturn(city);

        ConflictException e = assertThrows(ConflictException.class,
                ()-> citiesService.createCity(cityDTO));

        assertTrue(e.getMessage().contains("Cidade " + cityDTO.getName() + " já cadastrada"));

    }


    public CitiesDTO mockCityDTO(){
        return CitiesDTO.builder()
                .country("Brasil")
                .name("São Paulo")
                .build();
    }


}