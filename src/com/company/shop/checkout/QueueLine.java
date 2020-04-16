package com.company.shop.checkout;

import com.company.shop.customer.Customer;

import java.util.Queue;

public interface QueueLine {
    Queue<Customer> getQueue();
    void handleFirst();
    boolean isEmpty();

    void addToQueue(Customer customer);

    default void handleAll(){
        while (!isEmpty())
            handleFirst();
    }

    default String dateFormat(){
        return "yyyy-MM-dd'T'HH:mm:ss'Z'";
    }
}
