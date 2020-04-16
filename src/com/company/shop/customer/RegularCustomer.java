package com.company.shop.customer;

import com.company.shop.checkout.Cashier;
import com.company.shop.checkout.Checkout;

import java.util.Objects;

public class RegularCustomer extends Customer {

    public RegularCustomer(int id) {
        super(id);
    }

    @Override
    public Cashier findCashier() {
        return Objects.requireNonNull(Checkout.getInstance().getActiveCashiers().stream().reduce((a, b) ->
                a.getCustomersCount() < b.getCustomersCount() ? a: b).orElse(null));
    }
}
