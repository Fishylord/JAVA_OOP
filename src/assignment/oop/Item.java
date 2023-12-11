/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author User
 */
public class Item {
    private String foodId;
    private String accountId; 
    private String name;
    private double price;
    private String description;
    private double rating;
    private boolean isAvailable;

    public Item(String foodId, String accountId, String name, double price, String description, double rating, boolean isAvailable) {
        this.foodId = foodId;
        this.accountId = accountId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
        this.isAvailable = isAvailable;
    }

    

    @Override
    public String toString() {
        return foodId + "," + accountId + "," + name + "," + price + "," + description + "," + rating + "," + isAvailable;
    }
    
    public static String generateFoodId() {
        // This uses a UUID (universally unique identifier) for simplicity.
        return UUID.randomUUID().toString();
    }


    
    
    
    
    //Spam Return
        public String getFoodId() {
        return foodId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    //spam setting
    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    
    public static List<Item> loadAllItems() throws FileNotFoundException, IOException {
        List<Item> items = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("Food.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(",");
                // Assume the format is Food ID, Account ID, Name, Price, Description, Rating, Availability
                Item item = new Item(
                        itemData[0].trim(), // Food ID
                        itemData[1].trim(), // Account ID
                        itemData[2].trim(), // Name
                        Double.parseDouble(itemData[3].trim()), // Price
                        itemData[4].trim(), // Description
                        Double.parseDouble(itemData[5].trim()), // Rating
                        Boolean.parseBoolean(itemData[6].trim()) // Availability
                );
                items.add(item);
            }
        }
        return items;
    }
}
