package com.example.shop.servise.impl;

import com.example.shop.servise.CurrencyApiService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class CurrencyServiceImplTest {

    private static String responseBody;
    private static Map<String, Double> expected = new HashMap<>();
    static {
        expected.put("UAH", 10.551396);
        expected.put("PLN", 4.099294);
        expected.put("USD", 1.305302);
    }

    @BeforeClass
    public static void setUp() throws IOException {
        try {
            InputStream fileStream = CurrencyServiceImplTest.class.getResourceAsStream("service_response_body.json");
            BufferedReader bodyStream = new BufferedReader(
                    new InputStreamReader(fileStream)
            );
            responseBody = bodyStream.lines().collect(Collectors.joining());
            bodyStream.close();
        } catch (FileNotFoundException shouldntHappen) {}
    }

    private CurrencyApiService currencyApiService = new CurrencyServiceImpl() {
        @Override
        protected String getResponseBody(Instant forDate) throws IOException {
            return responseBody;
        }
    };

    @Test
    public void getPrice() {

        assertEquals(currencyApiService.getPrice("EUR", "UAH", Instant.now()), expected.get("UAH"), 0.00001);
        assertEquals(currencyApiService.getPrice("EUR", "PLN", Instant.now()), expected.get("PLN"), 0.00001);
        assertEquals(currencyApiService.getPrice("EUR", "USD", Instant.now()), expected.get("USD"), 0.00001);
        assertEquals(currencyApiService.getPrice("EUR", "EUR", Instant.now()), 1d, 0.00001);
        assertEquals(currencyApiService.getPrice("USD", "UAH", Instant.now()), expected.get("UAH")/expected.get("USD"), 0.0001);
        assertEquals(currencyApiService.getPrice("PLN", "EUR", Instant.now()), 1/expected.get("PLN"), 0.0001);



    }
}