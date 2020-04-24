package com.company.items.foods;

import java.time.LocalDate;

public class Milk extends Food {
    private double fatContent;
    private static final int expirationDays = 30;
    public Milk(String name, double price, LocalDate productionDate, double fatContent) {
        super(name, price, productionDate);
        this.fatContent = fatContent;
    }

    public double getFatContent() {
        return fatContent;
    }

    public void setFatContent(double fatContent) {
        this.fatContent = fatContent;
    }

    @Override
    public int getExpirationDays() {
        return expirationDays;
    }

    @Override
    public String getDescription(){
        return String.format("%sFat Content: %s\n", super.getDescription(), fatContent);
    }
}
