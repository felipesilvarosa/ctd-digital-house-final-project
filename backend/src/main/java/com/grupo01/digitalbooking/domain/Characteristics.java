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


    public Characteristics(CharacteristicsDTO characteristicsDTO) {
        this.id = characteristicsDTO.getId();
        this.name = characteristicsDTO.getName();
        this.icon = characteristicsDTO.getIcon();
    }
}
