package com.company.shop.customer;

import com.company.shop.checkout.Cashier;
import com.company.shop.checkout.Checkout;

import java.util.List;

public class LazyCustomer extends Customer{

    public LazyCustomer(int id) {
        super(id);
    }

    @Override
    public Cashier findCashier() {
        List<Cashier> cashiers = Checkout.getInstance().getActiveCashiers();
        if (cashiers.size() < 2){
            throw new IndexOutOfBoundsException();
        }
        return cashiers.get(0).getCustomersCount() < cashiers.get(1).getCustomersCount()
                ? cashiers.get(0) : cashiers.get(1);
    }
}
