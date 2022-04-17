package com.grupo01.digitalbooking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DigitalbookingApplicationTest {

    @Test
    void mainShouldNotThrowException(){
        assertDoesNotThrow(()->DigitalbookingApplication.main(new String[]{""}));
    }
}