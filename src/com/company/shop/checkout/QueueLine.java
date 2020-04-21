package com.company.shop.checkout;

import com.company.items.Item;
import com.company.util.Logger;
import com.company.shop.customer.Customer;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public interface QueueLine extends Serializable {
    Queue<Customer> getQueue();
    boolean isNotEmpty();
    void addToQueue(Customer customer);

    default void log(String msg){
        Logger.getInstance().log(msg);
    }
}
