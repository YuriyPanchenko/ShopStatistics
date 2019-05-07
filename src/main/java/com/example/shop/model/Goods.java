package com.example.shop.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;

@Entity
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private double price;
    private Instant date;
    private String currency;

    public Goods() {
    }

    public Goods(String name, double price, Instant date, String currency) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.currency = currency;
    }

    public Goods(double price, Instant date, String currency) {
        this.price = price;
        this.date = date;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Instant getInstantDate() {
        return date;
    }

    public String getDate() {
        return date.toString().replaceAll("T00:00:00Z","");
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


}
