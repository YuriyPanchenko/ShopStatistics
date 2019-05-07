package com.example.shop.servise.impl;
import com.example.shop.servise.CurrencyApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQueries;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyApiService {

    @Value("${fixer.io.accesskey}")
    private String accessKey;

    @Value("${fixer.io.apiurl}")
    private String apiUrl;

    @Value("${fixer.io.symbols}")
    private String symbols;


    @Autowired
    private DateTimeFormatter instantDateFormatter;

    private String buildUrl(Instant instant) {

        return apiUrl +
                instant.toString().replaceAll("T00:00:00Z","") + //draw this!
                "?access_key=" +
                accessKey +
                "&symbols=" +
                symbols;

    }

    @Override
    public double getPrice(String from, String to, Instant date) {
        if (to.equals(from))
            return 1;

        String urlString = buildUrl(date);
        try {

            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String responseBody = in.lines().collect(Collectors.joining());
            in.close();

            Map<String, Double> exchangeRates = new HashMap<>();
            //in this try we can catch not arrived date(we don't add price to total profit)
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.readTree(responseBody)
                        .get("rates")
                        .fields()
                        .forEachRemaining(jsonNodeEntry -> {
                            String currency = jsonNodeEntry.getKey();
                            Double rate = jsonNodeEntry.getValue().asDouble();
                            exchangeRates.put(currency, rate);
                        });
            }catch (Exception nullPointer){
                return 0;
            }



            //in this cases we at once return result
            if(from.equals("EUR"))
                return exchangeRates.get(to);
            if(to.equals("EUR")){
                return 1/exchangeRates.get(from);
            }
            return exchangeRates.get(from)/exchangeRates.get(to);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
