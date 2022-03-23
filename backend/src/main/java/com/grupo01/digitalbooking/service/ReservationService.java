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
        List<Reservation> response = reservationRepository.findAll();
        return response.stream().map(ReservationDTO::new).collect(Collectors.toList());
    }

    public ReservationDTO createReservation(ReservationDTO dto) {

        Optional<Reservation> reservationFound = reservationRepository.findByClient(new Client(dto.getClientId()));
        reservationFound.ifPresent(ignored->{
            throw new ConflictException("There already is a reservation under this client's id");
        });
        Reservation response = new Reservation(dto);
        response = reservationRepository.save(response);
        return new ReservationDTO(response);

    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }


    /**
     * TODO: Verificar se necessita regras de negocios para a edição
     *
     * */
    public ReservationDTO editReservation(ReservationDTO dto) {
        reservationRepository.findById(dto.getId())
                .orElseThrow(()-> new NotFoundException("Reservation not found"));
        Reservation response = new Reservation(dto);
        reservationRepository.save(response);
        return new ReservationDTO(response);

    }
}
