package com.company.shop.checkout;

import com.company.shop.customer.Customer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import java.util.LinkedList;

public class Exit implements QueueLine{

    private final Queue<Customer> queue;

    public Exit(){
        queue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public Queue<Customer> getQueue() {
        return queue;
    }

    @Override
    public void addToQueue(Customer customer) {
        if (!isNotEmpty()) {
            queue.add(customer);
            synchronized (this) {
                this.notify();
            }
        } else {
            queue.add(customer);
        }
    }

    public void handle() {
        String msg = messageBuilder(queue.remove().getId());
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
