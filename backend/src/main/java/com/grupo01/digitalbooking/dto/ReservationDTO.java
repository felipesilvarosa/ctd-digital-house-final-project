package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private String clientName;
    private Map<String, Object> reservationInfo;
    private LocalDateTime checkinDateTime;
    private LocalDateTime checkoutDateTime;

    public ReservationDTO(Reservation entity){
        this.clientName = entity.getClient().getFirstName() + " " + entity.getClient().getLastName();
        this.reservationInfo = Map.of(
                "productId",entity.getProduct().getId(),
                "name",entity.getProduct().getName(),
                "destination",entity.getProduct().getDestination().getCity() + ", " +
                        entity.getProduct().getDestination().getCountry()
        );
        this.checkinDateTime = entity.getCheckinDateTime();
        this.checkoutDateTime = entity.getCheckoutDateTime();
    }

}