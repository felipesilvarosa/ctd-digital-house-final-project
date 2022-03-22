package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {
    @Test
    public void validatePasswordShouldNotThrowExceptionWhenValidPassword(){

        assertDoesNotThrow(()->{
            Util.validatePassword("aA1@aaaaaa");
        });

    }
    @Test
    public void validatePasswordShouldThrowExceptionWhenInvalidPassword(){
        //less than 8 char
        assertThrows(BadRequestException.class,()->{
            Util.validatePassword("aA1@a");
        });
        //no lowercase
        assertThrows(BadRequestException.class,()->{
            Util.validatePassword("AA1@AAAAA");
        });
        //no uppercase
        assertThrows(BadRequestException.class,()->{
            Util.validatePassword("aa1@aaaaa");
        });
        //no number
        assertThrows(BadRequestException.class,()->{
            Util.validatePassword("aA@AAAAA");
        });
        //no special character
        assertThrows(BadRequestException.class,()->{
            Util.validatePassword("aA1AAAAAA");
        });

    }

}