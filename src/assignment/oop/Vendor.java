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

/**
 *
 * @author User
 */
public abstract class Vendor extends User implements UserFunctionalities {
    private List<Item> items; // A list to hold the vendor's items might be removed if unused
    
    public Vendor(String username, String password) {
        super(username, password);
        this.items = new ArrayList<>(); // Initialize the items list Might be removed if unused
    }
    
    public boolean addItem(Item item) {
        if (!duplicationCheck(item.getName())) {
            items.add(item);
            try (FileWriter fw = new FileWriter("Food.txt", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(item.toString());
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
        List<Item> availableItems = getAvailableItems();
        if (availableItems.isEmpty()) {
            System.out.println("You have nothing :( Start Adding");
            return;
        }
        for (Item item : availableItems) {
            System.out.println(item); // Assuming Item class has a proper toString() implementation
        }
    }
    @Override
    public void showFunctionalities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
    
    
    
    
    public boolean duplicationCheck(String itemName) {
        User currentUser = Session.getCurrentUser();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("Food.txt"))) {
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
    
    // This method loads the vendor's items from the file and checks for availability
    public List<Item> getAvailableItems() {
        List<Item> availableItems = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader("Food.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(",");
                // Assume the format is Food ID, Account ID, Name, Price, Description, Rating, Availability
                if (itemData[1].trim().equals(this.getUsername())) {
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
}
