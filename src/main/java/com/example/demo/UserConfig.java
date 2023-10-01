package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Value("${user.age.limit}")
    private int userAgeLimit;

    public int getUserAgeLimit()
    {
        return userAgeLimit;
    }
}
