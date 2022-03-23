package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Getter
@Setter
@NoArgsConstructor
public class UserDTO{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long roleId;
    private String firstName;
    private String lastName;
    private String email;

    public UserDTO(User entity){
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.roleId = entity.getRole().getId();
    }


}
