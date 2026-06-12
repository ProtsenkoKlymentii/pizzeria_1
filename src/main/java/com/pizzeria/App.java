package com.pizzeria;

import java.io.IOException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderDataManager dataManager = new OrderDataManager();
        Order currentOrder = null;
        String filePath = "order.csv";

        System.out.println("=== Welcome to Pizzeria Management System ===");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Create new order (Enter Customer)");
            System.out.println("2. Add pizzas to current order");
            System.out.println("3. View current order (Auto-sorts by price)");
            System.out.println("4. Export order to file (" + filePath + ")");
            System.out.println("5. Import order from file (" + filePath + ")");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();
                    try {
                        Customer customer = new Customer(name);
                        currentOrder = new Order(customer);
                        System.out.println("Success: Order initialized for " + customer.getName());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Validation Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    if (currentOrder == null) {
                        System.out.println("Error: Please create an order first (Option 1) or load one (Option 5).");
                        break;
                    }
                    runPizzaSelectionLoop(scanner, currentOrder);
                    break;

                case 3:
                    if (currentOrder == null) {
                        System.out.println("No active order to view.");
                    } else {
                        currentOrder.sortPizzasByPrice();
                        currentOrder.printOrder();
                    }
                    break;

                case 4:
                    if (currentOrder == null) {
                        System.out.println("Error: Nothing to export. Create and fill an order first.");
                        break;
                    }
                    try {
                        dataManager.exportToFile(currentOrder, filePath);
                        System.out.println("Success: Order sorted and exported to " + filePath);
                    } catch (IOException e) {
                        System.out.println("File Error during export: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        currentOrder = dataManager.importFromFile(filePath);
                        System.out.println("Success: Order loaded from " + filePath);
                        currentOrder.printOrder();
                    } catch (IOException e) {
                        System.out.println("File Error during import: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Data Error inside file: " + e.getMessage());
                    }
                    break;

                case 6:
                    running = false;
                    System.out.println("Thank you for using Pizzeria App. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }
        }

        scanner.close();
    }

    private static void runPizzaSelectionLoop(Scanner scanner, Order order) {
        while (true) {
            System.out.println("\nChoose pizza:");
            System.out.println("1. Margherita - $5");
            System.out.println("2. Pepperoni - $7");
            System.out.println("3. Hawaiian - $6");

            System.out.print("Your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid format. Try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    order.addPizza(new Pizza("Margherita", 5));
                    System.out.println("Added Margherita.");
                    break;
                case 2:
                    order.addPizza(new Pizza("Pepperoni", 7));
                    System.out.println("Added Pepperoni.");
                    break;
                case 3:
                    order.addPizza(new Pizza("Hawaiian", 6));
                    System.out.println("Added Hawaiian.");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

            System.out.println("\nDo you want to continue adding pizzas?");
            System.out.println("1 - Yes");
            System.out.println("0 - Finish adding");

            int next;
            try {
                next = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                next = 1;
            }

            if (next == 0) {
                break;
            }
        }
    }
}
