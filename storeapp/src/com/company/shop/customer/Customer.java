package com.company.shop.customer;

import com.company.items.Item;
import com.company.shop.cart.Cart;
import com.company.shop.checkout.Cashier;

import java.io.Serializable;
import java.util.List;

public abstract class Customer implements Serializable {
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

    public boolean isCartEmpty(){
        return cart == null;
    }

    public void addItem(Item item){
        if (cart == null){
            cart = new Cart();
        }
        cart.addItem(item);
    }

    public abstract void chooseQueue(List<Cashier> cashiers);

}
