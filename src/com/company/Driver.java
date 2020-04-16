package com.company;

import com.company.items.clothes.*;
import com.company.items.foods.*;
import com.company.shop.checkout.Cashier;
import com.company.shop.checkout.Checkout;
import com.company.shop.checkout.Exit;
import com.company.shop.customer.Customer;
import com.company.shop.customer.LazyCustomer;
import com.company.shop.customer.RegularCustomer;
import com.company.shop.customer.SmartCustomer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        Exit exit = Exit.getInstance();
        Checkout checkout = Checkout.getInstance();
        checkout.addCashier(Cashier.CashierState.ACTIVE);
        checkout.addCashier(Cashier.CashierState.ACTIVE);
        checkout.addCashier(Cashier.CashierState.DISABLED);

        List<Customer> customers = Arrays.asList(
                new RegularCustomer(1), new SmartCustomer(2), new LazyCustomer(3), new SmartCustomer(4), new RegularCustomer(5));
        customers.get(0).addItem(
                new Milk("milk", 322, LocalDate.of(2020, 4, 12), 3.2));
        customers.get(1).addItem(
                new Jeans("jeans", 1690, 44, "blue", "slim fit", "denim"));
        customers.get(1).addItem(
                new Sausage.Builder("sausage", 1355, LocalDate.of(2020, 12, 1)).setMeatGrade("good").build());
        customers.get(2).addItem(
                new Milk("milk", 322, LocalDate.of(2020, 4, 12), 3.2));
        customers.get(2).addItem(
                new Shirt("shirt", 6969, 46, "white", "regular"));
        customers.get(2).addItem(
                new Jeans("jeans", 1445, 46, "gray", "slim fit", "denim"));
        customers.get(3).addItem(
                new Jeans("jeans", 1690, 44, "blue", "slim fit", "denim"));


        customers.forEach(customer -> {
            try {
                customer.queueUp();
            }catch (NullPointerException | ArrayIndexOutOfBoundsException exception) {
                exit.addToQueue(customer);
            }
        });

        checkout.getActiveCashiers().forEach(Cashier::handleAll);
        exit.handleAll();
    }
}
