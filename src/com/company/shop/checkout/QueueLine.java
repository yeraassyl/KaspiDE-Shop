package com.company.shop.checkout;

import com.company.Logger;
import com.company.shop.customer.Customer;

import java.io.Serializable;
import java.util.Queue;

public interface QueueLine extends Serializable {
    Queue<Customer> getQueue();
    Customer retrieveFirst();
    boolean isEmpty();
    void addToQueue(Customer customer);
    String message(Customer customer);

    default void handleAll(){
        while (!isEmpty())
            handle();
    }

    default void handle(){
        Logger.getInstance().log(message(retrieveFirst()));
    }
}
