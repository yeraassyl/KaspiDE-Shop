package com.company.shop;

import com.company.items.Item;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Record implements Serializable {
    private final List<Item> items;
    private final long customerId;
    private final LocalDate date;
    private final double amount;

    public Record(List<Item> items, long customerId, double amount){
        this.items = items;
        this.customerId = customerId;
        this.date = LocalDate.now();
        this.amount = amount;
    }

    public List<Item> getItems() {
        return items;
    }

    public long getCustomerId() {
        return customerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}
