package com.company.shop.customer;

import com.company.shop.checkout.Cashier;

import java.util.List;
import java.util.Objects;

public class SmartCustomer extends Customer{

    public SmartCustomer(int id){
        super(id);
    }

    @Override
    public void chooseQueue(List<Cashier> cashiers) throws NullPointerException{
        Objects.requireNonNull(
            cashiers.stream().reduce((a, b) ->
                a.getQueue().stream().mapToInt(c->c.getCart().itemCount()).sum() <
                b.getQueue().stream().mapToInt(c->c.getCart().itemCount()).sum() ? a : b).orElse(null)
        ).addToQueue(this);
    }
}
