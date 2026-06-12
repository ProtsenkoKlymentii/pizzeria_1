package com.pizzeria;

import java.util.Objects;

public class Pizza {
    private String name;
    private double price;

    public Pizza(String name, double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Pizza price cannot be negative");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Pizza name cannot be null or empty");
        }
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Pizza name cannot be null or empty");
        }
        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Pizza price cannot be negative");
        }
        this.price = price;
    }

    public double calculateDiscountPrice(double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        return price - (price * discountPercent / 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza)) return false;
        Pizza pizza = (Pizza) o;
        return Double.compare(pizza.price, price) == 0 && Objects.equals(name, pizza.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
