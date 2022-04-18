package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.Client;
import com.grupo01.digitalbooking.domain.Reservation;
import com.grupo01.digitalbooking.dto.NewReservationDTO;
import com.grupo01.digitalbooking.dto.ReservationDTO;
import com.grupo01.digitalbooking.repository.ClientRepository;
import com.grupo01.digitalbooking.repository.ReservationRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;

    public List<ReservationDTO> getReservations() {
        return reservationRepository.findAll().
                stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }

    public ReservationDTO getReservationById(Long id) {
        return new ReservationDTO(reservationRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Nenhuma reserva com este id foi encontrada")));
    }

    public List<ReservationDTO> getReservationsByClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(()-> new NotFoundException("Nenhum cliente com este id foi encontrado"));
        List<Reservation> response =  reservationRepository.findAllByClient(client);
        if(response.isEmpty()) throw new NotFoundException("Nenhuma reserva foi encontrada para este cliente");
        return response.stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }

    public ReservationDTO createReservation(NewReservationDTO dto) {
        if(dto.getClientId()==null) throw new BadRequestException("Não pode criar uma reserva sem cliente");
        if(dto.getProductId()==null) throw new BadRequestException("Não pode criar uma reserva sem produto");

        LocalDateTime checkinDateTime = LocalDateTime.of(dto.getCheckinDate(),dto.getCheckinTime());
        LocalDateTime checkoutDateTime = LocalDateTime.of(dto.getCheckoutDate(),dto.getCheckoutTime());
        if(checkoutDateTime.isBefore(checkinDateTime))
            throw new BadRequestException("Horário e data de checkout não podem ser antes da data e horário de checkin");

        return new ReservationDTO(reservationRepository.save(new Reservation(dto)));
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public ReservationDTO editReservation(NewReservationDTO dto) {
        reservationRepository.findById(dto.getId())
                .orElseThrow(()-> new NotFoundException("Reserva não encontrada"));
        LocalDateTime checkinDateTime = LocalDateTime.of(dto.getCheckinDate(),dto.getCheckinTime());
        LocalDateTime checkoutDateTime = LocalDateTime.of(dto.getCheckoutDate(),dto.getCheckoutTime());
        if(checkoutDateTime.isBefore(checkinDateTime))
            throw new BadRequestException("Horário e data de checkout não podem ser antes da data e horário de checkin");
        return new ReservationDTO(reservationRepository.save(new Reservation(dto)));
    }
}
