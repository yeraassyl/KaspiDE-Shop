package com.company.items;

import java.io.Serializable;

public abstract class Item implements Serializable {
    private String name;
    private double price;
    public static final double AVERAGE_PRICE = 1400.0;

    public Item(String name, double price){
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getDescription(){
        return String.format("Name: %s\nPrice: %f\n", name, price);
    }
}
