package com.grupo01.digitalbooking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo01.digitalbooking.dto.DestinationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_destinations")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String country;
    private String latitude;
    private String longitude;

    @JsonIgnore
    @OneToMany(mappedBy = "destination",fetch = FetchType.LAZY)
    private List<Product> products;

    public Destination(DestinationDTO dto){
        this.id = dto.getId();
        this.country = dto.getCountry();
        this.city = dto.getCity();
    }

    public Destination(Long id) {
        this.id = id;
    }
}
