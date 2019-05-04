package com.example.shop.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class InstantDateFormat {

    @Value("${instant.date.format}")
    private String format;

    @Bean
    DateFormat simpleDateFormat() {
        return new SimpleDateFormat(format);
    }
}
