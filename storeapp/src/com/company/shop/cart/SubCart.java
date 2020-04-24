package com.company.shop.cart;

import com.company.items.Expirable;
import com.company.items.Item;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubCart<E extends Item> implements Serializable {
    private final ArrayList<E> items;

    public SubCart(){
        items = new ArrayList<E> ();
    }

    public ArrayList<E> getItems() {
        return items;
    }

    public void addItem(E elem){
        items.add(elem);
    }

    public double totalPrice(){
        return items.stream().mapToDouble(E::getPrice).sum();
    }

    public int itemCount(){
        return items.size();
    }

    public List<Item> notExpiredItems(){
        return items.stream()
                .filter(item -> item instanceof Expirable)
                .filter(item -> LocalDate.now().isBefore(((Expirable)item).getExpirationDate()))
                .collect(Collectors.toList());
    }
}
