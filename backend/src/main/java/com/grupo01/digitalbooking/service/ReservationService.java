package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.Client;
import com.grupo01.digitalbooking.domain.Reservation;
import com.grupo01.digitalbooking.dto.ReservationDTO;
import com.grupo01.digitalbooking.repository.ReservationRepository;
import com.grupo01.digitalbooking.service.exceptions.ConflictException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<ReservationDTO> getReservations() {
        List<Reservation> categories = reservationRepository.findAll();
        return categories.stream().map(ReservationDTO::new).collect(Collectors.toList());
    }

    public ReservationDTO createReservation(ReservationDTO dto) {

        Reservation reservation = new Reservation(dto);
        Optional<Reservation> reservationFound = reservationRepository.findByClient(new Client(dto.getClientId()));

        if (reservationFound.isPresent())
            throw new ConflictException("There already is a reservation under this client's id");

        reservationRepository.save(reservation);
        return new ReservationDTO(reservation);

    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }


    /**
     * TODO: Verificar se necessita regras de negocios para a edição
     *
     * */
    public ReservationDTO editReservation(ReservationDTO dto) {
        Reservation reservation = new Reservation(dto);
        Optional<Reservation> reservationFound = reservationRepository.findById(reservation.getId());
        if(reservationFound.isPresent()) {
            reservationRepository.save(reservation);
            return new ReservationDTO(reservation);
        } else {
            throw new NotFoundException("Reservation not found");
        }
    }
}
