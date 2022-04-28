package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Reservation;
import com.grupo01.digitalbooking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findAllByClient(User client);

}
