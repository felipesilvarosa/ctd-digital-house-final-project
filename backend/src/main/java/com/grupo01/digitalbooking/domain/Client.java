package com.grupo01.digitalbooking.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tb_clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends User{

    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;

    public Client(Long id){
        super(id);
    }

    public Client(User parent) {
        this.setId(parent.getId());
        this.setAccountNonExpired(parent.isAccountNonExpired());
        this.setEnabled(parent.isEnabled());
        this.setPassword(getPassword());
        this.setAccountNonLocked(parent.isAccountNonLocked());
        this.setCredentialsNonExpired(parent.isCredentialsNonExpired());
        this.setRole(parent.getRole());
        this.setEmail(parent.getEmail());
        this.setFirstName(parent.getFirstName());
        this.setLastName(parent.getLastName());
    }
}
