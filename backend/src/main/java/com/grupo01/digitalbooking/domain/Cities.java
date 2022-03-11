package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.CitiesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_cities")
public class Cities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;

    public Cities(CitiesDTO dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.country = dto.getCountry();
    }
}
