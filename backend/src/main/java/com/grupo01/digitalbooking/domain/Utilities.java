package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.UtilitiesDTO;
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
@Table(name = "tb_characteristics")
public class Utilities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Utilities(UtilitiesDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }

    public Utilities(Long id) {
        this.id = id;
    }
}
