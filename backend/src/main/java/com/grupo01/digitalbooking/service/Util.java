package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.service.exceptions.BadRequestException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static void validatePassword(String password) {
        String password_pattern ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,}$";
        Pattern pattern = Pattern.compile(password_pattern);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()){
            throw new BadRequestException(
                    "Invalid password. " +
                            "The password must have "+
                            "a minimum of 8 characters, " +
                            "1 lower case character, " +
                            "1 upper case character, " +
                            "1 number and 1 special character");
        }

    }

}
