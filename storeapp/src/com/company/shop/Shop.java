package com.company.shop;

import com.company.items.Expirable;
import com.company.items.Item;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.concurrent.CopyOnWriteArrayList;

public class Shop implements Serializable {
    private final List<Cashier> cashiers;
    private final List<Item> soldItems;
    private final List<Record> records;
    private final Exit exit;
    private final ConcurrentHashMap<Customer, Boolean> customerDiscount;
    public static ExecutorService es;
    public static final double DISCOUNT_PERCENTAGE = 5;
    ReentrantLock lock = new ReentrantLock();
    Condition shopClosed = lock.newCondition();

    public Shop(){
        cashiers = new CopyOnWriteArrayList<>();
        soldItems = new CopyOnWriteArrayList<>();
        records = new CopyOnWriteArrayList<>();
        exit = new Exit();
        customerDiscount = new ConcurrentHashMap<>();
    }

    public void addCustomer(Customer customer){
        if (!customerDiscount.containsKey(customer)){
            customerDiscount.put(customer, Boolean.FALSE);
        }
    }

    public void addCashier(Cashier cashier){
        cashiers.add(cashier);
    }

    public List<Cashier> getActiveCashiers(){
        return cashiers.stream().filter(Cashier::isActive).collect(Collectors.toList());
    }

    public void initExecutor(){
        es = Executors.newFixedThreadPool(cashiers.size() + 2);
    }

    public void queueUpCustomers(){
        es.execute(() -> customerDiscount.keySet().forEach(customer -> {
            try {
                customer.chooseQueue(getActiveCashiers());
            } catch (NullPointerException | ArrayIndexOutOfBoundsException exception) {
                exit.addToQueue(customer);
            }
        }));
    }

    public void activateCashiers(){
        getActiveCashiers().forEach(cashier -> {
            es.execute(() -> {
                try {
                    while(!Thread.interrupted()){
                        synchronized (cashier) {
                            while (!cashier.isNotEmpty()) {
                                cashier.wait();
                            }
                            Record record = cashier.handle(customerDiscount);
                            records.add(record);
                            soldItems.addAll(record.getItems());
                            Thread.sleep(3000);
                        }
                    }
                } catch (InterruptedException e) {
                    String err = String.format("Thread #%d has been Interrupted", Thread.currentThread().getId());
                    Logger.getInstance().log(err);
                }
            });
        });
    }

    public void activateExit(){
        es.execute(() -> {
            try {
                while(!Thread.interrupted()){
                    synchronized (exit) {
                        while (!exit.isNotEmpty()) {
                            exit.wait();
                        }
                        exit.handle();
                        Thread.sleep(1000);
                    }
                }
            } catch (InterruptedException e){
                String err = String.format("Thread #%d has been Interrupted", Thread.currentThread().getId());
                Logger.getInstance().log(err);
            }
        });
    }

    public void openShop(){
        initExecutor();
        activateExit();
        activateCashiers();
        queueUpCustomers();
    }

    public void closeShop(){
        try {
            if (!es.awaitTermination(120, TimeUnit.SECONDS)){
                es.shutdownNow();
            }
        } catch (InterruptedException e) {
            Logger.getInstance().log("Shop closed with interrupting cashiers");
        }

        makeReport();
        processRecords();
    }

    public void processRecords(){
        HashMap<Long, Double> customerTotalSum = new HashMap<>();

        records.parallelStream()
                .filter(record -> record.getDate().plusDays(10).isAfter(LocalDate.now()))
                .forEach(record -> {
                        double amount = customerTotalSum.getOrDefault(record.getCustomerId(), 0.0);
                        customerTotalSum.put(record.getCustomerId(), amount + record.getAmount());
                });

        customerDiscount.keySet().forEach(customer -> {
            boolean discount = customerTotalSum.getOrDefault(customer.getId(), 0.0) > Item.AVERAGE_PRICE * 5;
            customerDiscount.put(customer, discount);
        });

    }

    public void makeReport(){
        moreExpensiveThan(Item.AVERAGE_PRICE);
        expiringAfter(10);
    }

    public void moreExpensiveThan(double price){
        soldItems.stream()
                .filter(item -> item.getPrice() > price)
                .forEach(item ->
                    Logger.getInstance().log(String.format("Name %s; Price: %f, Exp. Date: -----", item.getName(), item.getPrice()))
                );
    }

    public void expiringAfter(int days){
        soldItems.stream()
                .filter(item -> item instanceof Expirable)
                .filter(item -> ((Expirable) item).goingToExpire(days))
                .forEach(item ->
                    Logger.getInstance().log(String.format(
                            "Name %s; Price: %f, Exp. Date: %s",
                            item.getName(),
                            item.getPrice(),
                            ((Expirable) item).getExpirationDate().format(DateTimeFormatter.ISO_LOCAL_DATE))));
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
        Shop shop = new Shop();
        shop.addCashier(new Cashier(1, Cashier.CashierState.ACTIVE));
        shop.addCashier(new Cashier(2, Cashier.CashierState.ACTIVE));
        shop.addCashier(new Cashier(3, Cashier.CashierState.DISABLED));

        List<Customer> customers = new ArrayList<>();
        customers.add(new RegularCustomer(1));
        customers.add(new SmartCustomer(2));
        customers.add(new LazyCustomer(3));
        customers.add(new SmartCustomer(4));
        customers.add(new RegularCustomer(5));

        customers.get(0).addItem(
                new Milk("milk", 322, LocalDate.of(2020, 4, 29), 3.2));
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

        customers.forEach(shop::addCustomer);
        return shop;
    }
}
