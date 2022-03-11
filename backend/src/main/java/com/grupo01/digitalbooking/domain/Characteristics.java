package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.CharacteristicsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_characteristics")
public class Characteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String icon;


    public Characteristics(CharacteristicsDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.icon = dto.getIcon();
    }
}
