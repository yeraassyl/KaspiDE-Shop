package com.company.items;

public abstract class Item{
    private String name;
    private double price;

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
