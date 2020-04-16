package com.company.shop.customer;

import com.company.items.Item;
import com.company.shop.cart.Cart;
import com.company.shop.checkout.Cashier;

public abstract class Customer {
    private final long id;
    private Cart cart;

    public Customer(int id){
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public long getId() {
        return id;
    }

    public void addItem(Item item){
        if (cart == null){
            cart = new Cart();
        }
        cart.addItem(item);
    }

    public abstract Cashier findCashier();

    public void queueUp(){
        if (cart == null)
            throw new NullPointerException();
        findCashier().addToQueue(this);
    }

}
