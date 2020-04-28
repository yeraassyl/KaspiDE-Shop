package com.company.shop.cart;
import com.company.items.Expirable;
import com.company.items.Item;
import com.company.items.foods.Food;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Cart implements Serializable {
    private final List<Item> items;

    public Cart(){
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public int itemCount(){
        return items.size();
    }

    public double totalPrice(){
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    public double totalPrice(double discountPercentage){
        return totalPrice() * (1.00 - discountPercentage);
    }

    public List<Item> notExpiredItems(){
        return items.stream()
                .filter(item -> item instanceof Expirable)
                .filter(item -> LocalDate.now().isBefore(((Expirable)item).getExpirationDate()))
                .collect(Collectors.toList());
    }
}

