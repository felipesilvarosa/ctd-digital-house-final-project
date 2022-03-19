package com.grupo01.digitalbooking.dto;

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
    private Long clientId;
    private Long productId;

    public ReservationDTO(Reservation entity){
        this.id = entity.getId();
        this.clientId = entity.getClient().getId();
        this.productId = entity.getProduct().getId();
    }

}
