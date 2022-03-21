package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.ReservationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_reservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Client client;
    @OneToOne
    private Product product;

    public Reservation(ReservationDTO dto){
        this.id = dto.getId();
        this.client = dto.getClient();
        this.product = dto.getProduct();
    }

}
