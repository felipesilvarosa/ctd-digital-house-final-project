package com.grupo01.digitalbooking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo01.digitalbooking.dto.CityDTO;
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
@Table(name = "tb_cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;

    @JsonIgnore
    @OneToMany(mappedBy = "city",fetch = FetchType.LAZY)
    private List<Product> products;

    public City(CityDTO dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.country = dto.getCountry();
    }
}
