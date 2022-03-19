package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.ReservationDTO;
import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.SUCCESS;

@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping()
    public ResponseEntity<DefaultResponseDTO> findAllReservations() {
        List<ReservationDTO> response = reservationService.getReservations();
        Map<String, List<ReservationDTO>> data = Map.of("reservation", response);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, data, "Reservations retrieved successfully"));
    }

    @PostMapping("/new")
    public ResponseEntity<DefaultResponseDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO response = reservationService.createReservation(reservationDTO);
        Map<String, ReservationDTO> data = Map.of("reservation", response);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, data, "Reservation created successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DefaultResponseDTO> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, "Reservation deleted successfully"));
    }

    @PutMapping("/edit")
    public ResponseEntity<DefaultResponseDTO> editReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO response = reservationService.editReservation(reservationDTO);
        Map<String, ReservationDTO> data = Map.of("reservation", response);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, data, "Reservation edited successfully"));
    }
}
