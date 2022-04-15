package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Product;
import com.grupo01.digitalbooking.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewReservationDTO {

    private Long id;
    private Long clientId;
    private Product product;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;

}