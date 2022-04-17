package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.Client;
import com.grupo01.digitalbooking.domain.Destination;
import com.grupo01.digitalbooking.domain.Product;
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
    private Client clientWithReservation;
    private Client clientWithoutReservation;

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
        this.clientWithReservation = new Client(existingId);
        this.clientWithReservation.setReservations(List.of(new Reservation()));
        this.clientWithoutReservation = new Client(emptyId);
        List<Reservation> findAllResponse = new ArrayList<>();
        Reservation saveResponse = new Reservation();
        Product mockProduct = new Product(existingId);
        mockProduct.setDestination(new Destination(existingId));
        saveResponse.setId(1L);
        saveResponse.setClient(new Client(existingId));
        saveResponse.setProduct(mockProduct);
        when(clientRepository.findById(existingId)).thenReturn(Optional.of(new Client(existingId)));
        when(clientRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(reservationRepository.findAll()).thenReturn(findAllResponse);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(saveResponse);
        when(reservationRepository.findAllByClient(clientWithReservation)).thenReturn(clientWithReservation.getReservations());
        when(reservationRepository.findAllByClient(clientWithoutReservation)).thenReturn(List.of());
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
        testInput.setProductId(existingId);
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
        testInput.setCheckinDate(validDate);
        testInput.setCheckinTime(validTime);
        testInput.setCheckoutDate(validDate);
        testInput.setCheckoutTime(validTime);
        ReservationDTO testOutput = service.editReservation(testInput);
        assertNotNull(testOutput);
    }


}