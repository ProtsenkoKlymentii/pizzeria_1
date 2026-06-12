package com.pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class OrderTest {

    @Test
    void testAddPizza() {
        Customer c = new Customer("Klim");
        Order order = new Order(c);

        order.addPizza(new Pizza("Margherita", 5));

        assertEquals(1, order.getPizzas().size());
    }

    @Test
    void testTotalPrice() {
        Customer c = new Customer("Klim");
        Order order = new Order(c);

        order.addPizza(new Pizza("Margherita", 5));
        order.addPizza(new Pizza("Pepperoni", 7));

        assertEquals(12, order.getTotalPrice());
    }

    @Test
    void testEmptyOrder() {
        Customer c = new Customer("Klim");
        Order order = new Order(c);

        assertEquals(0, order.getTotalPrice());
    }

    @Test
    void testPizzaEquals() {
        Pizza p1 = new Pizza("Margherita", 5);
        Pizza p2 = new Pizza("Margherita", 5);

        assertEquals(p1, p2);
    }

    @Test
    void testPizzaNotEquals() {
        Pizza p1 = new Pizza("Margherita", 5);
        Pizza p2 = new Pizza("Pepperoni", 7);

        assertNotEquals(p1, p2);
    }

    @Test
    void testCustomerEquals() {
        Customer c1 = new Customer("Klim");
        Customer c2 = new Customer("Klim");

        assertEquals(c1, c2);
    }

    @Test
    void testCustomerNotEquals() {
        Customer c1 = new Customer("Klim");
        Customer c2 = new Customer("Ivan");

        assertNotEquals(c1, c2);
    }

    @Test
    void testVipCustomerLogic() {
        Customer c = new Customer("Alexander");
        assertTrue(c.isVipCustomer());
    }

    @Test
    void testNonVipCustomerLogic() {
        Customer c = new Customer("Tom");
        assertFalse(c.isVipCustomer());
    }

    @Test
    void testMultiplePizzas() {
        Customer c = new Customer("Klim");
        Order order = new Order(c);

        for (int i = 0; i < 5; i++) {
            order.addPizza(new Pizza("Margherita", 5));
        }

        assertEquals(5, order.getPizzas().size());
    }

    @Test
    void testAddNullPizza() {
        Customer c = new Customer("Klim");
        Order order = new Order(c);

        assertThrows(IllegalArgumentException.class, () -> {
            order.addPizza(null);
        });
    }
    @Test
    void testNegativePricePizza() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Pizza("BadPizza", -5);
        });
    }
    @Test
    void testEmptyCustomerName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("");
        });
    }
}