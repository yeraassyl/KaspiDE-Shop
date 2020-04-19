package com.company.shop.cart;
import com.company.items.Item;
import com.company.items.foods.Food;

import java.io.Serializable;


public class Cart implements Serializable {
    private final SubCart<Food> foods;
    private final SubCart<Item> goods;

    public Cart(){
        this.foods = new SubCart<Food>();
        this.goods = new SubCart<Item>();
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

    public int itemCount(){
        return foods.itemCount() + goods.itemCount();
    }

    public double totalPrice(){
        return foods.totalPrice() + goods.totalPrice();
    }

}

