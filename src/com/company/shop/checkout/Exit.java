package com.company.shop.checkout;

import com.company.shop.customer.Customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Exit implements QueueLine {

    private static final Exit instance = new Exit();
    private final LinkedList<Customer> queue;

    private Exit(){
        queue = new LinkedList<Customer>();
    }

    public static Exit getInstance(){
        return instance;
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
    public void handleFirst() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_TIME)
                + " Customer " + queue.removeFirst().getId() + "is leaving");
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }


}
