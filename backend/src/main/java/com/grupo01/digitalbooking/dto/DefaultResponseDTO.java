package com.grupo01.digitalbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class DefaultResponseDTO {

    private Status status;
    private Map data;
    private String message;


    public DefaultResponseDTO(Status failed, String messsage) {
        this.status = failed;
        this.data = Map.of();
        this.message = messsage;
    }

    public enum Status{
        FAILED,
        SUCCESS
    }

}
