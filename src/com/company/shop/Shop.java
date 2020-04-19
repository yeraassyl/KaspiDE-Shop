package com.company.shop;

import com.company.shop.checkout.Cashier;
import com.company.shop.checkout.Checkout;
import com.company.shop.checkout.Exit;
import com.company.shop.customer.Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Shop implements Serializable {
    private final List<Customer> customers;
    private final Checkout checkout;
    private final Exit exit;

    public Shop(){
        customers = new ArrayList<Customer>();
        checkout = new Checkout();
        exit = new Exit();
    }

    public Shop(List<Customer> customers, Checkout checkout){
        this.customers = customers;
        this.checkout = checkout;
        exit = new Exit();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void queueUpCustomers(){
        customers.forEach(customer -> {
            try {
                customer.chooseQueue(checkout.getActiveCashiers());
            }
            catch (NullPointerException | ArrayIndexOutOfBoundsException exception){
                exit.addToQueue(customer);
            }
        });
    }

    public void serveCustomers(){
        queueUpCustomers();
        checkout.getActiveCashiers().forEach(Cashier::handleAll);
        exit.handleAll();
    }
}
