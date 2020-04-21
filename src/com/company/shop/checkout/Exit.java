package com.company.shop.checkout;

import com.company.shop.customer.Customer;

import java.util.LinkedList;

public class Exit implements QueueLine {

    private final LinkedList<Customer> queue;

    public Exit(){
        queue = new LinkedList<>();
    }

    @Override
    public LinkedList<Customer> getQueue() {
        return queue;
    }

    @Override
    public void addToQueue(Customer customer) {
        queue.add(customer);
    }

    public void handleAll(){
        while(isNotEmpty()){
            handle(queue.removeFirst());
        }
    }

    public void handle(Customer customer) {
        String msg = messageBuilder(customer.getId());
        log(msg);
    }

    public String messageBuilder(long customerId){
        return String.format("Exit: Customer %d is leaving", customerId);
    }

    @Override
    public boolean isNotEmpty() {
        return !queue.isEmpty();
    }

}
