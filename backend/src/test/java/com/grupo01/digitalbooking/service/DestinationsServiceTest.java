package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.Destination;
import com.grupo01.digitalbooking.dto.DestinationDTO;
import com.grupo01.digitalbooking.repository.DestinationRepository;
import com.grupo01.digitalbooking.service.exceptions.ConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DestinationsServiceTest {

    @InjectMocks
    private DestinationsService destinationsService;
    @Mock
    private DestinationRepository destinationRepository;



    @Test
    void createShouldCreateNewDestination(){
        DestinationDTO destinationDTO = mockDestinationDTO();
        destinationsService.createDestination(destinationDTO);
        verify(destinationRepository, Mockito.times(1)).save(any());

    }

    @Test
    void createShouldFailWhenDestinationAlreadyExists(){
        DestinationDTO destinationDTO = mockDestinationDTO();
        Optional<Destination> destination = Optional.of(new Destination(destinationDTO));

        when(destinationRepository.findByCityAndCountry(destinationDTO.getCity(),destinationDTO.getCountry())).thenReturn(destination);

        assertThrows(ConflictException.class,
                ()-> destinationsService.createDestination(destinationDTO));
    }

    @Test

    void getDestinationShouldReturnListOfDestinationDTO(){
        when(destinationRepository.findAll()).thenReturn(List.of());
        List<DestinationDTO> testOutput = destinationsService.getDestinations();
        assertNotNull(testOutput);
    }


    public DestinationDTO mockDestinationDTO(){
        return DestinationDTO.builder()
                .country("Brasil")
                .city("São Paulo")
                .build();
    }


}