package com.company.items.clothes;

import com.company.items.Item;

public abstract class Clothes extends Item {

    private int size;
    private String color;

    public Clothes(String name, double price, int size, String color) {
        super(name, price);
        this.size = size;
        this.color = color;
    }

    public int getSize(){
        return this.size;
    }

    public String getColor(){
        return this.color;
    }

    public void setSize(int size){
        this.size = size;
    }

    public void setColor(String color){
        this.color = color;
    }

    @Override
    public String getDescription(){
        return String.format("%sSize: %s\nColor: %s\n", super.getDescription(), size, color);
    }
}
