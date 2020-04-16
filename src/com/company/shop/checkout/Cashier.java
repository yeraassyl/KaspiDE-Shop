package com.company.shop.checkout;


import com.company.shop.customer.Customer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Cashier implements QueueLine{

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
        this.queue = new LinkedList<Customer>();
    }

    public CashierState getState() {
        return state;
    }

    public void setState(CashierState state) {
        this.state = state;
    }

    public LinkedList<Customer> getQueue() {
        return queue;
    }

    public int getCustomersCount(){
        return queue.size();
    }

    @Override
    public void addToQueue(Customer customer) {
        queue.add(customer);
    }

    public boolean isActive(){
        return state == CashierState.ACTIVE;
    }

    @Override
    public void handleFirst() {
        Customer customer = queue.removeFirst();
        String receipt = String.format(
                "%s Cashier %d:Receipt of customer %d: %f",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME),
                this.id,
                customer.getId(),
                customer.getCart().totalPrice());
        System.out.println(receipt);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

}