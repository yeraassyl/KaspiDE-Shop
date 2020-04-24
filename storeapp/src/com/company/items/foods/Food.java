package com.company.items.foods;

import com.company.items.Expirable;
import com.company.items.Item;

import java.time.LocalDate;

public abstract class Food extends Item implements Expirable {
    private LocalDate productionDate;
    public Food(String name, double price, LocalDate productionDate){
        super(name, price);
        this.productionDate = productionDate;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    @Override
    public String getDescription(){
        return String.format("%sIssue Date: %s\nExpiration Date: %s\n", super.getDescription(), productionDate, getExpirationDays());
    }
}
