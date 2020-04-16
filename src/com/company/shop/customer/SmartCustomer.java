package com.company.shop.customer;

import com.company.shop.checkout.Cashier;
import com.company.shop.checkout.Checkout;

import java.util.Objects;

public class SmartCustomer extends Customer{

    public SmartCustomer(int id){
        super(id);
    }

    @Override
    public Cashier findCashier() {
        return Objects.requireNonNull(Checkout.getInstance().getActiveCashiers().stream().reduce((a, b) ->
                a.getQueue().stream().mapToInt(c->c.getCart().itemCount()).sum() <
                b.getQueue().stream().mapToInt(c->c.getCart().itemCount()).sum() ? a : b).orElse(null));
    }
}
