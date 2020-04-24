package com.company.items.foods;

import java.time.LocalDate;

public class Sausage extends Food {
    private String meatGrade;
    private static final int expirationDays = 90;

    public Sausage(String name, double price, LocalDate productionDate, String meatGrade) {
        super(name, price, productionDate);
        this.meatGrade = meatGrade;
    }

    public String getMeatGrade() {
        return meatGrade;
    }

    public void setMeatGrade(String meatGrade) {
        this.meatGrade = meatGrade;
    }

    @Override
    public int getExpirationDays(){
        return expirationDays;
    }

    @Override
    public String getDescription(){
        return String.format("%sMeat Grade: %s\n", super.getDescription(), meatGrade);
    }

    public static class Builder {
        private final String name;
        private final double price;
        private final LocalDate productionDate;
        private String meatGrade;
        public Builder(String name, double price, LocalDate productionDate){
            this.name = name;
            this.price = price;
            this.productionDate = productionDate;
        }
        public Builder setMeatGrade(String meatGrade){
            this.meatGrade = meatGrade;
            return this;
        }
        public Sausage build() {
            return new Sausage(name ,price, productionDate, meatGrade);
        }
    }
}
