package com.company.shop.checkout;


import com.company.items.Item;
import com.company.shop.Record;
import com.company.shop.Shop;
import com.company.shop.cart.Cart;
import com.company.shop.customer.Customer;

import java.util.*;

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

    public List<Record> handleAll(HashMap<Customer, Boolean> customerDiscount) {
        Customer customer;
        List<Record> records = new ArrayList<>();
        while (!isNotEmpty()){
            customer = queue.removeFirst();
            boolean discount = customerDiscount.get(customer);
            records.add(handle(customer, discount));
        }
        return records;
    }

    public Record handle(Customer customer, boolean discount){
        Cart cart = customer.getCart();
        long customerId = customer.getId();
        double totalPrice = cart.totalPrice();
        double totalDiscountPrice;
        String msg;
        if (discount){
            totalDiscountPrice = cart.totalPrice(Shop.DISCOUNT_PERCENTAGE);
            msg = messageBuilder(customerId, totalPrice, totalDiscountPrice);
        }
        else{
            msg = messageBuilder(customerId, totalPrice);
        }
        log(msg);

        return new Record(cart.getItems(), customerId, totalPrice);
    }


    public String messageBuilder(long customerId, double totalPrice){
        return String.format("Cashier %d, Receipt of customer %d:\nTotal Price - %f", id, customerId, totalPrice);
    }

    public String messageBuilder(long customerId, double totalPrice, double totalDiscountPrice){
        return String.format("%s, Price with discount - %f", messageBuilder(customerId, totalPrice), totalDiscountPrice);
    }

    @Override
    public boolean isNotEmpty() {
        return !queue.isEmpty();
    }
}
