package com.pizzeria;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class OrderDataManager {

    public void exportOrder(Order order, Writer writer) throws IOException {
        if (order == null || writer == null) {
            throw new IllegalArgumentException("Order and Writer cannot be null");
        }

        order.sortPizzasByPrice();

        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        bufferedWriter.write("CUSTOMER," + order.getCustomer().getName());
        bufferedWriter.newLine();

        for (Pizza pizza : order.getPizzas()) {
            bufferedWriter.write("PIZZA," + pizza.getName() + "," + pizza.getPrice());
            bufferedWriter.newLine();
        }

        bufferedWriter.flush();
    }

    public Order importOrder(Reader reader) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null");
        }

        BufferedReader bufferedReader = new BufferedReader(reader);
        Customer customer = null;
        Order order = null;
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 2) continue;

            String recordType = parts[0];

            if ("CUSTOMER".equals(recordType)) {
                customer = new Customer(parts[1]);
                order = new Order(customer);
            } else if ("PIZZA".equals(recordType) && order != null) {
                String pizzaName = parts[1];
                double price = Double.parseDouble(parts[2]);
                order.addPizza(new Pizza(pizzaName, price));
            }
        }

        if (order == null) {
            throw new IOException("Invalid file format: Customer data is missing.");
        }

        return order;
    }

    public void exportToFile(Order order, String filePath) throws IOException {
        try (FileWriter fw = new FileWriter(filePath)) {
            exportOrder(order, fw);
        }
    }

    public Order importFromFile(String filePath) throws IOException {
        try (FileReader fr = new FileReader(filePath)) {
            return importOrder(fr);
        }
    }
}