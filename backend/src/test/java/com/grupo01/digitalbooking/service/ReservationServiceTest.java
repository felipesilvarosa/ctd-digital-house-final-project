package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.Client;
import com.grupo01.digitalbooking.domain.Reservation;
import com.grupo01.digitalbooking.dto.NewReservationDTO;
import com.grupo01.digitalbooking.dto.ReservationDTO;
import com.grupo01.digitalbooking.repository.ClientRepository;
import com.grupo01.digitalbooking.repository.ReservationRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService service;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ClientRepository clientRepository;
    private Long existingId;
    private Long nonExistingId;
    private Long emptyId;
    private NewReservationDTO testInput;
    private LocalDate validDate;
    private LocalDate invalidDate;
    private LocalTime validTime;
    private LocalTime invalidTime;

    @BeforeEach
    void init(){
        this.existingId = 5L;
        this.nonExistingId = 0L;
        this.emptyId = 1L;
        this.testInput = new NewReservationDTO();
        this.validDate = LocalDate.of(2020,1,1);
        this.invalidDate = LocalDate.of(2000,12,31);
        this.validTime = LocalTime.of(12,0);
        this.invalidTime = LocalTime.of(0,0);
        List<Reservation> findAllResponse = new ArrayList<>();
        Reservation saveResponse = new Reservation();
        saveResponse.setId(1L);
        saveResponse.setClient(new Client(existingId));
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(new Client(existingId)));
//        when(clientRepository.findById(nonExistingId)).thenReturn(Optional.of());
        when(reservationRepository.findAll()).thenReturn(findAllResponse);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(saveResponse);
        when(reservationRepository.findByClient(new Client(existingId))).thenReturn(Optional.of(new Reservation()));
        when(reservationRepository.findByClient(new Client(emptyId))).thenReturn(Optional.empty());
        when(reservationRepository.findById(existingId)).thenReturn(Optional.of(new Reservation()));
        when(reservationRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        doNothing().when(reservationRepository).deleteById(any(Long.class));
    }

    @Test
    void getReservationsShouldReturnDTOList(){
        List<ReservationDTO> testOutput = service.getReservations();
        assertNotNull(testOutput);
    }
    @Test
    void getReservationByIdShouldThrowExceptionWhenInvalidId(){
        assertThrows(NotFoundException.class,()->service.getReservationById(nonExistingId));
    }
    @Test
    void getReservationByIdShouldReturnDTOWhenValidId(){
        assertNotNull(service.getReservationById(existingId));
    }
    @Test
    void getReservationsByClientShouldReturnDTOListWhenValidId(){
        assertNotNull(service.getReservationsByClient(existingId));
    }
    @Test
    void getReservationsByClientShouldThrowExceptionWhenInvalidId(){
//        assertThrows()
    }
    @Test
    void createReservationShouldThrowExceptionWhenInvalidParams(){
        testInput.setClientId(existingId);
        assertThrows(BadRequestException.class,()->service.createReservation(testInput));
    }
    @Test
    void createReservationShouldReturnDTOWhenClientDoesNotExist(){
        testInput.setClientId(nonExistingId);
        ReservationDTO testOutput = service.createReservation(testInput);
        assertNotNull(testOutput);
    }
    @Test
    void deleteReservationShouldCallRepositoryDeleteByIdOnce(){
        service.deleteReservation(1L);
        verify(reservationRepository,times(1)).deleteById(1L);
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
        assertNotNull(testOutput);
    }


}