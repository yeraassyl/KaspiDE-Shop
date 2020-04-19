package com.company;

import com.company.items.clothes.*;
import com.company.items.foods.*;
import com.company.shop.Shop;
import com.company.shop.checkout.Cashier;
import com.company.shop.checkout.Checkout;
import com.company.shop.checkout.Exit;
import com.company.shop.customer.Customer;
import com.company.shop.customer.LazyCustomer;
import com.company.shop.customer.RegularCustomer;
import com.company.shop.customer.SmartCustomer;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        Shop shop ;
        Logger LOG = Logger.getInstance();

        try (FileInputStream fis = new FileInputStream("shop.ser")){
            ObjectInputStream in = new ObjectInputStream(fis);
            shop = (Shop)in.readObject();
            in.close();
            fis.close();

            LOG.log("Successful deserialization");
            Customer customer = new RegularCustomer(6);
            customer.addItem(new Milk("milk", 322, LocalDate.of(2020, 4, 12), 3.2));
            shop.addCustomer(customer);

            try (FileOutputStream fos = new FileOutputStream("shop.ser")){
                ObjectOutputStream out = new ObjectOutputStream(fos);
                out.writeObject(shop);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            shop.serveCustomers();

        } catch (IOException | ClassNotFoundException e) {
            LOG.log("Deserialization not successful");

            Checkout checkout = new Checkout();
            checkout.addCashier(Cashier.CashierState.ACTIVE);
            checkout.addCashier(Cashier.CashierState.ACTIVE);
            checkout.addCashier(Cashier.CashierState.DISABLED);

            List<Customer> customers = new ArrayList<Customer>();
            customers.add(new RegularCustomer(1));
            customers.add(new SmartCustomer(2));
            customers.add(new LazyCustomer(3));
            customers.add(new SmartCustomer(4));
            customers.add(new RegularCustomer(5));

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


            shop = new Shop(customers, checkout);

            shop.serveCustomers();

            try (FileOutputStream fos = new FileOutputStream("shop.ser")) {
                ObjectOutputStream out = new ObjectOutputStream(fos);
                out.writeObject(shop);
                out.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        }
    }
}
