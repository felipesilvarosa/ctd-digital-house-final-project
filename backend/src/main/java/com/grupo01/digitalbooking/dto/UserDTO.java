package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class UserDTO{

    private Long id;
    private String role;
    private String firstName;
    private String lastName;
    private String email;

    public UserDTO(User entity){
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.role = entity.getRole().getRole();
    }


}
