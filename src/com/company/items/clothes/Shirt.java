package com.company.items.clothes;

public class Shirt extends Clothes {
    private String collarType;
    public Shirt(String name, double price, int size, String color, String collarType) {
        super(name, price, size, color);
        this.collarType = collarType;
    }

    public String getCollarType() {
        return collarType;
    }

    public void setCollarType(String collarType) {
        this.collarType = collarType;
    }

    @Override
    public String getDescription(){
        return String.format("%sCollar Type: %s\n", super.getDescription(), collarType);
    }
}
