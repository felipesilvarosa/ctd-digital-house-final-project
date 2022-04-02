package com.grupo01.digitalbooking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DigitalbookingApplicationTest {

    @Test
    void mainShouldNotThrowException(){
        assertDoesNotThrow(()->DigitalbookingApplication.main(new String[]{""}));
    }
}