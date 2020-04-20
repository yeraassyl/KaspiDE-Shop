package com.company.shop.checkout;


import com.company.shop.customer.Customer;

import java.util.LinkedList;

public class Cashier implements QueueLine {

    public enum CashierState {
        ACTIVE("Cashier is working"),
        DISABLED("Cashier is not working");

        private final String description;
        CashierState(String description){
            this.description = description;
        }
        public String getDescription(){
            return description;
        }
    }

    private final int id;
    private CashierState state;
    private final LinkedList<Customer> queue;

    public Cashier(int id, CashierState state){
        this.id = id;
        this.state = state;
        this.queue = new LinkedList<>();
    }

    public CashierState getState() {
        return state;
    }

    public void setState(CashierState state) {
        this.state = state;
    }

    public int getCustomersCount(){
        return queue.size();
    }

    public boolean isActive(){
        return state == CashierState.ACTIVE;
    }

    @Override
    public LinkedList<Customer> getQueue() {
        return queue;
    }

    @Override
    public void addToQueue(Customer customer) {
        queue.add(customer);
    }

    @Override
    public Customer retrieveFirst(){
        return queue.removeFirst();
    }

    public String message(Customer customer){
        return String.format(
                "Cashier %d: Receipt of customer %d - %f",
                this.id,
                customer.getId(),
                customer.getCart().totalPrice());
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
