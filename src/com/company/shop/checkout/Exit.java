package com.company.shop.checkout;

import com.company.shop.customer.Customer;

import java.util.LinkedList;

public class Exit implements QueueLine {

    private final LinkedList<Customer> queue;

    public Exit(){
        queue = new LinkedList<Customer>();
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

    @Override
    public String message(Customer customer) {
        return String.format("Customer %d is leaving", customer.getId());
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

}
