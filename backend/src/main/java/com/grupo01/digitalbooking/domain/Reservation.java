package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.NewReservationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_reservations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Product product;
    private LocalDateTime checkinDateTime;
    private LocalDateTime checkoutDateTime;

    public Reservation(NewReservationDTO dto){
        this.id = dto.getId();
        this.client = new Client(dto.getClientId());
        this.product = new Product(dto.getProductId());
        this.checkinDateTime = LocalDateTime.of(dto.getCheckinDate(),dto.getCheckinTime());
        this.checkoutDateTime = LocalDateTime.of(dto.getCheckoutDate(),dto.getCheckoutTime());
    }

}
