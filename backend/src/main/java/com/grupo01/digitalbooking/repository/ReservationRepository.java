package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Client;
import com.grupo01.digitalbooking.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Optional<Reservation> findByClient(Client client);

}
