package com.company.items.foods;

import java.time.LocalDate;

public class Kefir extends Food {
    private int fatContent;
    private static final int expirationDays = 30;
    public Kefir(String name, double price, LocalDate productionDate, int fatContent) {
        super(name, price, productionDate);
        this.fatContent = fatContent;
    }

    public int getFatContent() {
        return fatContent;
    }

    public void setFatContent(int fatContent) {
        this.fatContent = fatContent;
    }

    public int getExpirationDays() {
        return expirationDays;
    }

    @Override
    public String getDescription(){
        return String.format("%sFat Content: %s\n", super.getDescription(), fatContent);
    }
}
