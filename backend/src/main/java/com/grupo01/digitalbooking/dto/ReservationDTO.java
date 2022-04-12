package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Product;
import com.grupo01.digitalbooking.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Long id;
    private Long clientId;
    private Product product;
    private LocalDate reservationDate;

    public ReservationDTO(Reservation entity){
        this.id = entity.getId();
        this.clientId = entity.getClient().getId();
        this.product = entity.getProduct();
    }

}