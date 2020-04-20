package com.company;

import com.company.items.clothes.*;
import com.company.items.foods.*;
import com.company.shop.Shop;
import com.company.shop.checkout.Cashier;
import com.company.shop.customer.Customer;
import com.company.shop.customer.LazyCustomer;
import com.company.shop.customer.RegularCustomer;
import com.company.shop.customer.SmartCustomer;
import com.company.util.Logger;
import sun.security.provider.SHA;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        Logger.setOutputTarget(Logger.Target.FILE);
        Shop shop = Shop.load();
        if (shop != null){
            Customer customer = new RegularCustomer(6);
            customer.addItem(new Milk("milk", 322, LocalDate.of(2020, 4, 12), 3.2));
            shop.addCustomer(customer);
        }
        else{
            shop = Shop.init();
        }
        shop.save();
        shop.serveCustomers();
    }
}
