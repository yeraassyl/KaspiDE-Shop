package com.company.items;

import com.company.items.Expirable;
import com.company.items.Item;

import java.time.LocalDate;

public class CleaningAgent extends Item implements Expirable {
    private static final int expirationDays = 365 * 2;
    private LocalDate productionDate;
    public CleaningAgent(String name, double price, LocalDate productionDate) {
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
    public int getExpirationDays() {
        return expirationDays;
    }

    @Override
    public String getDescription() {
        return String.format("%sIssue Date:%s\nExpiration Date:%s\n", super.getDescription(), productionDate, getExpirationDate());
    }
}
