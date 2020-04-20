package com.company.shop;

import com.company.items.clothes.Jeans;
import com.company.items.clothes.Shirt;
import com.company.items.foods.Milk;
import com.company.items.foods.Sausage;
import com.company.shop.checkout.Cashier;
import com.company.shop.checkout.Exit;
import com.company.shop.customer.Customer;
import com.company.shop.customer.LazyCustomer;
import com.company.shop.customer.RegularCustomer;
import com.company.shop.customer.SmartCustomer;
import com.company.util.Logger;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Shop implements Serializable {
    private final List<Cashier> cashiers;
    private final List<Customer> customers;
    private final Exit exit;

    public Shop(){
        customers = new ArrayList<>();
        cashiers = new ArrayList<>();
        exit = new Exit();
    }

    public Shop(List<Cashier> cashiers, List<Customer> customers){
        this.cashiers = cashiers;
        this.customers = customers;
        exit = new Exit();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public void addCashier(Cashier cashier){
        cashiers.add(cashier);
    }

    public List<Cashier> getActiveCashiers(){
        return cashiers.stream().filter(Cashier::isActive).collect(Collectors.toList());
    }

    public void queueUpCustomers(){
        customers.forEach(customer -> {
            try {
                customer.chooseQueue(getActiveCashiers());
            }
            catch (NullPointerException | ArrayIndexOutOfBoundsException exception){
                exit.addToQueue(customer);
            }
        });
    }

    public void serveCustomers(){
        queueUpCustomers();
        getActiveCashiers().forEach(Cashier::handleAll);
        exit.handleAll();
    }

    public void save(){
        try (FileOutputStream fos = new FileOutputStream("shop.ser")){
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Shop load() {
        Shop instance = null;
        try (FileInputStream fis = new FileInputStream("shop.ser")) {
            ObjectInputStream in = new ObjectInputStream(fis);
            instance = (Shop) in.readObject();
            in.close();
            fis.close();
            Logger.getInstance().log("Shop object have been successfully deserialized");
        }
        catch (IOException | ClassNotFoundException e){
            Logger.getInstance().log("Something went wrong" + e);
        }
        return instance;
    }

    public static Shop init(){
        List<Cashier> cashiers = new ArrayList<>();
        cashiers.add(new Cashier(1, Cashier.CashierState.ACTIVE));
        cashiers.add(new Cashier(2, Cashier.CashierState.ACTIVE));
        cashiers.add(new Cashier(3, Cashier.CashierState.DISABLED));

        List<Customer> customers = new ArrayList<>();
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

        return new Shop(cashiers, customers);
    }
}
