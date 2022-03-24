package com.grupo01.digitalbooking.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_available_date")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDate {

    @Id
    private LocalDate value;
    @ManyToMany
    private List<Product> productList;

    public AvailableDate(LocalDate value){
        this.value = value;
    }

}
