package com.company.shop.checkout;

import com.company.shop.customer.Customer;
import com.company.shop.customer.SmartCustomer;
import sun.awt.image.ImageWatched;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Checkout implements Serializable {

    private final List<Cashier> cashiers;
    private static int idCounter = 1;
    public Checkout(){
        cashiers = new ArrayList<>();
    }

    public Checkout(ArrayList<Cashier> cashiers){
        this.cashiers = cashiers;
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public void addCashier(Cashier.CashierState state){
        cashiers.add(new Cashier(idCounter++, state));
    }

    public List<Cashier> getActiveCashiers(){
        return cashiers.stream().filter(Cashier::isActive).collect(Collectors.toList());
    }

}
