package com.example.shop.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class InstantDateFormat {

    @Value("${instant.date.format}")
    private String format;

    @Bean
    DateFormat simpleDateFormat() {
        SimpleDateFormat formatObj = new SimpleDateFormat(format);
        formatObj.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return formatObj;
    }



    @Bean
    DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern(format);
    }
}
