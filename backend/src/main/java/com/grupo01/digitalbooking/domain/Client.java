package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.NewUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tb_client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends User{

    @OneToMany
    private List<Reservation> reservation;

    public Client(NewUserDTO dto){
        super(dto);
    }

    public Client(Long id){
        super(id);
    }

}
