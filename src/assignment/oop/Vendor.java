/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 *
 * @author User
 */
public class Vendor extends User {
    private List<Item> items; 
    private Scanner scanner;
    
    public Vendor(String username, String password, String userID) {
        super(username, password, userID);
        this.items = new ArrayList<>();
        this.scanner = new Scanner(System.in); 
    }
    
    @Override
    public void displayMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("=========Vendor Menu=======");
            System.out.println("1. Add/Edit/Delete Item");
            System.out.println("2. Accept/Cancle Order");
            System.out.println("3. Update orde Status");
            System.out.println("4. Check Order History");
            System.out.println("5. Read Customer Review");
            System.out.println("6. Revenue Dashboard");
            System.out.println("0. Exit");
            
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    int manageChoice = -1;
                    while (manageChoice != 0) {
                        System.out.println("==== Manage Items ====");
                        System.out.println("1. Add Item");
                        System.out.println("2. Edit Item");
                        System.out.println("3. Delete Item");
                        System.out.println("4. Display Items");
                        System.out.println("0. Return to Main Menu");

                        System.out.print("Enter your choice: ");
                        manageChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (manageChoice) {
                            case 1:
                                addItem();
                                break;
                            case 2:
                                editItem();
                                break;
                            case 3:
                                deleteItem();
                                break;
                            case 4:
                                displayAvailableItems();
                                break;
                            case 0:
                                manageChoice = 0; //exits loop bruh
                                return; 
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    }
                    break;
                case 2:
                
                    break;
                case 3:
                    // Display available items
                    displayAvailableItems();
                    break;
                case 0:
                    System.out.println("Exiting vendor menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    
    public boolean addItem() {
        System.out.println("Enter the name of the item:");
        String name = scanner.nextLine();
        System.out.println("Enter the price of the item:");
        double price = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.println("Enter the description of the item:");
        String description = scanner.nextLine();
        System.out.println("Is the item available? (true/false):");
        boolean availability = scanner.nextBoolean();
        scanner.nextLine(); 
        Item newItem = new Item(UUID.randomUUID().toString(), getUserID(), name, price, description, 0, availability);
        if (!duplicationCheck(newItem.getName())) {
            items.add(newItem);
            try (FileWriter fw = new FileWriter("C:\\Users\\User\\Documents\\NetBeansProjects\\Assignment OOP\\src\\assignment\\oop\\Food.txt", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(newItem.toString());
            } catch (IOException e) {
                // Handle the exception
                System.out.println("An error occurred while writing to Food.txt.");
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            System.out.println("An item with the same name already exists for this vendor.");
            return false;
        }
    }
    
    public void removeItem(Item item){
        
    }
    
    public void displayAvailableItems() {
        List<Item> allAvailableItems = loadAvailableItems(); // Load all available items once
        int page = 0;

        while (true) {
            int start = page * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, allAvailableItems.size());
            System.out.println("=========Page " + (page + 1) + " of " + ((allAvailableItems.size() - 1) / PAGE_SIZE + 1) + "=========");
            List<Item> pageItems = allAvailableItems.subList(start, end);

            for (Item item : pageItems) {
                System.out.println(item); 
            }

            System.out.println("=========Page " + (page + 1) + " of " + ((allAvailableItems.size() - 1) / PAGE_SIZE + 1) + "=========");
            if (!pageItems.isEmpty()) {
                System.out.println("1. Next");
            }
            if (page > 0) {
                System.out.println("2. Previous");
            }
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1 && end < allAvailableItems.size()) {
                page++; 
            } else if (choice == 2 && page > 0) {
                page--; 
            } else if (choice == 0) {
                break; 
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private List<Item> loadAvailableItems() {
        List<Item> availableItems = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\User\\Documents\\NetBeansProjects\\Assignment OOP\\src\\assignment\\oop\\Food.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(",");
                if (itemData[1].trim().equals(this.getUserID())) { // Use getUserID() to check the owner
                    boolean isAvailable = Boolean.parseBoolean(itemData[6].trim());
                    if (isAvailable) {
                        Item item = new Item(
                                itemData[0].trim(), // Food ID
                                itemData[1].trim(), // Account ID
                                itemData[2].trim(), // Name
                                Double.parseDouble(itemData[3].trim()), // Price
                                itemData[4].trim(), // Description
                                Double.parseDouble(itemData[5].trim()), // Rating
                                isAvailable // Availability
                        );
                        availableItems.add(item);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from Food.txt.");
            e.printStackTrace();
        }
        return availableItems;
    }
    
    public boolean duplicationCheck(String itemName) {
        String currentUser = getUserID();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\User\\Documents\\NetBeansProjects\\Assignment OOP\\src\\assignment\\oop\\Food.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(","); //Check Accound ID if Name already exist only if it doesn't it returns fine to allow for adding
                if (itemData[1].trim().equals(this.getUsername()) && itemData[2].trim().equalsIgnoreCase(itemName)) {
                    return true; //A Previous Name isfound
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from Food.txt.");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void Financial_Dashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void deleteItem() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void editItem() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
