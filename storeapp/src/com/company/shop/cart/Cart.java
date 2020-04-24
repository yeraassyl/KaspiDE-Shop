package com.company.shop.cart;
import com.company.items.Item;
import com.company.items.foods.Food;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Cart implements Serializable {
    private final SubCart<Food> foods;
    private final SubCart<Item> goods;

    public Cart(){
        this.foods = new SubCart<>();
        this.goods = new SubCart<>();
    }

    public SubCart<Food> getFoods() {
        return foods;
    }

    public SubCart<Item> getGoods() {
        return goods;
    }

    public void addItem(Item item){
        if (item instanceof Food) foods.addItem((Food) item);
        else goods.addItem(item);
    }

    public List<Item> getItems(){
        List<Item> items = new ArrayList<>(goods.getItems());
        items.addAll(goods.getItems());
        return items;
    }

    public int itemCount(){
        return foods.itemCount() + goods.itemCount();
    }

    public double totalPrice(){
        return foods.totalPrice() + goods.totalPrice();
    }

    public double totalPrice(double discountPercentage){
        return (foods.totalPrice() + goods.totalPrice()) * (1.00 - discountPercentage);
    }
}

