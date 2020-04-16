package com.company.shop.checkout;

import com.company.shop.customer.Customer;
import com.company.shop.customer.SmartCustomer;
import sun.awt.image.ImageWatched;

import java.util.*;
import java.util.stream.Collectors;

public class Checkout {

    private static Checkout instance;

    private final List<Cashier> cashiers;
    private static int idCounter = 1;
    private Checkout(){
        cashiers = new ArrayList<>();
    }

    public static Checkout getInstance() {
        if (instance == null){
            instance = new Checkout();
        }
        return instance;
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
