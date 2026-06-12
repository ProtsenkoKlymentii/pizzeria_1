package com.pizzeria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Order {

    private Customer customer;
    private List<Pizza> pizzas = new ArrayList<>();

    public Order(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Order must have a customer");
        }
        this.customer = customer;
    }

    // Возвращаем геттеры и сеттеры, которые ожидает OrderDataManager
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Order must have a customer");
        }
        this.customer = customer;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void addPizza(Pizza pizza) {
        if (pizza == null) {
            throw new IllegalArgumentException("Cannot add a null pizza to the order");
        }
        pizzas.add(pizza);
    }

    public void sortPizzasByPrice() {
        pizzas.sort(Comparator.comparingDouble(Pizza::getPrice));
    }

    public double getTotalPrice() {
        double total = 0;
        for (Pizza p : pizzas) {
            total += p.getPrice();
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(customer, order.customer) &&
                Objects.equals(pizzas, order.pizzas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, pizzas);
    }

    public void printOrder() {
        System.out.println("\n=== ORDER ===");
        System.out.println("Customer: " + customer.getName());

        for (Pizza p : pizzas) {
            System.out.println("- " + p.getName() + " ($" + p.getPrice() + ")");
        }

        System.out.println("Total: $" + getTotalPrice());
    }
}