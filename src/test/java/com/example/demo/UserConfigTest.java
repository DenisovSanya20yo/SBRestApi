package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"user.age.limit=18"})
public class UserConfigTest {

    @Value("${user.age.limit}")
    private int userAgeLimit;

    @Test
    void testGetUserAgeLimit() {
        assertEquals(18, userAgeLimit);
    }
}