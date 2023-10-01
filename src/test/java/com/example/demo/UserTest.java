package com.example.demo;

import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testGettersAndSetters() throws ParseException {

        User user = new User();
        user.setUserId("123");
        user.setFirstName("John");
        user.setLastName("Doe");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date birthDate = dateFormat.parse("01.01.1990");
        user.setBirthDate(birthDate);

        user.setPhoneNum("+380123456789");

        assertEquals("123", user.getUserId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals("+380123456789", user.getPhoneNum());
    }
}