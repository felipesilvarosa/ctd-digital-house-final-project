package com.grupo01.digitalbooking.controller.exceptionhandling;

import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.service.exceptions.ConflictException;
import com.grupo01.digitalbooking.service.exceptions.ForbiddenException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.FAILED;

@ControllerAdvice
public class CustomControllerAdvisor{

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<DefaultResponseDTO> handleForbiddenException (ForbiddenException ex){
        return ResponseEntity.status(403).body(new DefaultResponseDTO(FAILED,ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultResponseDTO> handleNotFoundException(NotFoundException ex){
        return ResponseEntity.status(404).body(new DefaultResponseDTO(FAILED,ex.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<DefaultResponseDTO> handleConflictException(ConflictException ex){
        return ResponseEntity.status(409).body(new DefaultResponseDTO(FAILED,ex.getMessage()));
    }

}