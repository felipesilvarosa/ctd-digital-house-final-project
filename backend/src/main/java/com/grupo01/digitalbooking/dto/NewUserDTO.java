package com.grupo01.digitalbooking.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class NewUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;


}
