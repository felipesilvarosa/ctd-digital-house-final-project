package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.Client;
import com.grupo01.digitalbooking.domain.Reservation;
import com.grupo01.digitalbooking.dto.ReservationDTO;
import com.grupo01.digitalbooking.repository.ReservationRepository;
import com.grupo01.digitalbooking.service.exceptions.ConflictException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService service;
    @Mock
    private ReservationRepository repository;
    private Long existingId;
    private Long nonExistingId;
    private ReservationDTO testInput;

    @BeforeEach
    void init(){
        this.existingId = 5L;
        this.nonExistingId = 0L;
        this.testInput = new ReservationDTO();
        List<Reservation> findAllResponse = new ArrayList<>();
        Reservation saveResponse = new Reservation();
        saveResponse.setId(1L);
        saveResponse.setClient(new Client(existingId));
        when(repository.findAll()).thenReturn(findAllResponse);
        when(repository.save(any(Reservation.class))).thenReturn(saveResponse);
        when(repository.findByClient(new Client(existingId))).thenReturn(Optional.of(new Reservation()));
        when(repository.findByClient(new Client(nonExistingId))).thenReturn(Optional.empty());
        when(repository.findById(existingId)).thenReturn(Optional.of(new Reservation()));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        doNothing().when(repository).deleteById(any(Long.class));
    }

    @Test
    void getReservationsShouldReturnDTOList(){
        List<ReservationDTO> testOutput = service.getReservations();
        assertNotNull(testOutput);
    }
    @Test
    void createReservationShouldThrowExceptionWhenClientExists(){
        testInput.setClientId(existingId);
        assertThrows(ConflictException.class,()->service.createReservation(testInput));
    }
    @Test
    void createReservationShouldReturnDTOWhenClientDoesNotExist(){
        testInput.setClientId(nonExistingId);
        ReservationDTO testOutput = service.createReservation(testInput);
        assertEquals(1L,testOutput.getId());
    }
    @Test
    void deleteReservationShouldCallRepositoryDeleteByIdOnce(){
        service.deleteReservation(1L);
        verify(repository,times(1)).deleteById(1L);
    }

    @Test
    void editReservationShouldThrowExceptionWhenIdDoesNotExist(){
        testInput.setId(nonExistingId);
        assertThrows(NotFoundException.class,()->service.editReservation(testInput));
    }
    @Test
    void editReservationShouldReturnDTOWhenIdExists(){
        testInput.setId(existingId);
        ReservationDTO testOutput = service.editReservation(testInput);
        assertEquals(5L,testOutput.getId());
    }


}