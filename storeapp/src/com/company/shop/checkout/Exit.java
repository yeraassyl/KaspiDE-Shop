package com.company.shop.checkout;

import com.company.shop.customer.Customer;
import com.company.util.Logger;

import java.util.LinkedList;

public class Exit implements QueueLine, Runnable{

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

    //handleAll -> run
    @Override
    public void run(){
        while(isNotEmpty()){
            handle(queue.removeFirst());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log("Something went wrong");
            }
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
