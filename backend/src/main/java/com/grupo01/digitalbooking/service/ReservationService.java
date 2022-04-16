package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.Client;
import com.grupo01.digitalbooking.domain.Reservation;
import com.grupo01.digitalbooking.dto.NewReservationDTO;
import com.grupo01.digitalbooking.dto.ReservationDTO;
import com.grupo01.digitalbooking.repository.ReservationRepository;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<ReservationDTO> getReservations() {
        List<Reservation> response = reservationRepository.findAll();
        return response.stream().map(ReservationDTO::new).collect(Collectors.toList());
    }

    public List<ReservationDTO> getReservationsByClient(Long id) {
        List<Reservation> response = reservationRepository.findAllByClient(new Client(id));
        return response.stream().map(ReservationDTO::new).collect(Collectors.toList());
    }

    public ReservationDTO createReservation(NewReservationDTO dto) {

        Reservation response = new Reservation(dto);
        response = reservationRepository.save(response);
        return new ReservationDTO(response);

    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public ReservationDTO editReservation(NewReservationDTO dto) {
        reservationRepository.findById(dto.getId())
                .orElseThrow(()-> new NotFoundException("Reservation not found"));
        Reservation response = new Reservation(dto);
        reservationRepository.save(response);
        return new ReservationDTO(response);
    }
}
