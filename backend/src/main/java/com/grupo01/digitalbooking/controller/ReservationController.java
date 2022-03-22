package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.dto.ReservationDTO;
import com.grupo01.digitalbooking.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> findAllReservations() {
        List<ReservationDTO> response = reservationService.getReservations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO response = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DefaultResponseDTO> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/edit")
    public ResponseEntity<ReservationDTO> editReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO response = reservationService.editReservation(reservationDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
