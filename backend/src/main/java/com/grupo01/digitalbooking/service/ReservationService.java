package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.Product;
import com.grupo01.digitalbooking.domain.Reservation;
import com.grupo01.digitalbooking.domain.Role;
import com.grupo01.digitalbooking.domain.User;
import com.grupo01.digitalbooking.dto.NewReservationDTO;
import com.grupo01.digitalbooking.dto.ReservationDTO;
import com.grupo01.digitalbooking.repository.ProductRepository;
import com.grupo01.digitalbooking.repository.ReservationRepository;
import com.grupo01.digitalbooking.repository.UserRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ReservationDTO> getReservations() {
        return reservationRepository.findAll().
                stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public ReservationDTO getReservationById(Long id) {
        return new ReservationDTO(reservationRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Nenhuma reserva com este id foi encontrada")));
    }

    @Transactional(readOnly = true)
    public List<ReservationDTO> getReservationsByClient(Long id) {
        User client = userRepository.findById(id).orElseThrow(()-> new NotFoundException("Nenhum cliente com este id foi encontrado"));
        List<Reservation> response =  reservationRepository.findAllByClient(client);
        if(response.isEmpty()) throw new NotFoundException("Nenhuma reserva foi encontrada para este cliente");
        return response.stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationDTO createReservation(NewReservationDTO dto) {
        if(dto.getClientId()==null) throw new BadRequestException("Não pode criar uma reserva sem cliente");
        if(dto.getProductId()==null) throw new BadRequestException("Não pode criar uma reserva sem produto");

        LocalDateTime checkinDateTime = LocalDateTime.of(dto.getCheckinDate(),dto.getCheckinTime());
        LocalDateTime checkoutDateTime = LocalDateTime.of(dto.getCheckoutDate(),dto.getCheckoutTime());
        if(checkoutDateTime.isBefore(checkinDateTime))
            throw new BadRequestException("Horário e data de checkout não podem ser antes da data e horário de checkin");
        User client = userRepository.findById(dto.getClientId()).orElseThrow(
                ()-> new BadRequestException("Usuário não encontrado"));
        if(client.getRole().toString().equals("USER")){
            client.setRole(new Role(2L,"CLIENT"));
            System.out.println(client.getId());
            client = userRepository.save(client);
            System.out.println(client.getId());
        }
        Product product = productRepository.getById(dto.getProductId());
        Reservation response = new Reservation(dto);
        response.setClient(client);
        response.setProduct(product);
        response = reservationRepository.save(response);
        return new ReservationDTO(response);
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

        Reservation savedReservation = reservationRepository.save(new Reservation(dto));
        Optional<Reservation> reservation = reservationRepository.findById(savedReservation.getId());
        return new ReservationDTO(reservation.get());
    }
}
