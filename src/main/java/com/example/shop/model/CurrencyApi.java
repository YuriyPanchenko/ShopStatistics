package com.example.shop.model;

public class CurrencyApi {
    private double priceInNecessaryCurrency;

    public CurrencyApi() {
    }

    public CurrencyApi(double priceInNecessaryCurrency) {
        this.priceInNecessaryCurrency = priceInNecessaryCurrency;
    }

    public double getPriceInNecessaryCurrency() {
        return priceInNecessaryCurrency;
    }

    public void setPriceInNecessaryCurrency(double priceInNecessaryCurrency) {
        this.priceInNecessaryCurrency = priceInNecessaryCurrency;
    }
}
