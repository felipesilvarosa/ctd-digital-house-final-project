package com.grupo01.digitalbooking.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_unavailable_dates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnavailableDate {

    @Id
    private LocalDate value;

}
