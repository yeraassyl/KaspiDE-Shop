package com.company.shop.customer;

import com.company.shop.checkout.Cashier;
import com.company.shop.checkout.Checkout;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class RegularCustomer extends Customer {

    public RegularCustomer(int id) {
        super(id);
    }

    @Override
    public void chooseQueue(List<Cashier> cashiers) throws NullPointerException{
        if (isCartEmpty())
            throw new NullPointerException();
        Objects.requireNonNull(cashiers.stream().reduce((a, b) ->
                a.getCustomersCount() < b.getCustomersCount() ? a : b).orElse(null)).addToQueue(this);
    }
}
