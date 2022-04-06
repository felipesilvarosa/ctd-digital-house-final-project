package com.grupo01.digitalbooking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo01.digitalbooking.dto.LocationDTO;
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
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String country;

    @JsonIgnore
    @OneToMany(mappedBy = "location",fetch = FetchType.LAZY)
    private List<Product> products;

    public Location(LocationDTO dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.country = dto.getCountry();
    }

    public Location(Long id) {
        this.id = id;
    }
}
