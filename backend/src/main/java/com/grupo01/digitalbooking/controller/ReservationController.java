package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.domain.Reservation;
import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.dto.NewReservationDTO;
import com.grupo01.digitalbooking.dto.ReservationDTO;
import com.grupo01.digitalbooking.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
@Api(value ="", tags = {"Reservas"})
@Tag(name ="Reservas", description="End point para controle de reservas")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> findAllReservations() {
        List<ReservationDTO> response = reservationService.getReservations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<ReservationDTO>> findReservationsByClient(@PathVariable Long id) {
        List<ReservationDTO> response = reservationService.getReservationsByClient(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody NewReservationDTO newReservationDTO) {
        ReservationDTO response = reservationService.createReservation(newReservationDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<ReservationDTO> editReservation(@RequestBody NewReservationDTO newReservationDTO) {
        ReservationDTO response = reservationService.editReservation(newReservationDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
