package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Client;
import com.grupo01.digitalbooking.domain.Product;
import com.grupo01.digitalbooking.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Long id;
    private Client client;
    private Product product;

    public ReservationDTO(Reservation entity){
        this.id = entity.getId();
        this.client = entity.getClient();
        this.product = entity.getProduct();
    }

}
