package com.example.shop.servise.impl;

import com.example.shop.model.Goods;
import com.example.shop.servise.CurrencyApiService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class CurrencyServiceImplTest {


    @Autowired
    private DateFormat instantDateFormat;

    @Autowired
    private CurrencyApiService currencyApiService;

    @Test
    public void ToEurApiService() throws ParseException {
        Date dateObj = instantDateFormat.parse("12/12/2012");
        Goods testEurGoods = new Goods(1, dateObj.toInstant(), "EUR" );
        Goods testUahGoods = new Goods(1, dateObj.toInstant(), "UAH" );
        Goods testPlnGoods = new Goods(1, dateObj.toInstant(), "PLN" );
        Goods testUsdGoods = new Goods(1, dateObj.toInstant(), "USD" );


        Assert.assertEquals(currencyApiService.getPrice(testEurGoods.getCurrency(),"EUR", testEurGoods.getInstantDate()), 1, 0);
        Assert.assertEquals(currencyApiService.getPrice(testUahGoods.getCurrency(),"EUR", testEurGoods.getInstantDate()), 0.095, 0.05);
        Assert.assertEquals(currencyApiService.getPrice(testPlnGoods.getCurrency(),"EUR", testEurGoods.getInstantDate()), 0.244, 0.05);
        Assert.assertEquals(currencyApiService.getPrice(testUsdGoods.getCurrency(),"EUR", testEurGoods.getInstantDate()), 0.766, 0.05);
    }
}