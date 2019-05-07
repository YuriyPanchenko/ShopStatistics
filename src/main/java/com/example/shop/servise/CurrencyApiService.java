package com.example.shop.servise;

import java.time.Instant;

public interface CurrencyApiService {
    double getPrice(String from, String to, Instant date);
}
