/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 *
 * @author User
 */
public class Customer extends User{
    private final Scanner scanner;
    
    public Customer(String username, String password, String userID) {
        super(username, password, userID);
        this.scanner = new Scanner(System.in); 
    }

    @Override
    public void displayMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("=========Customer Menu========");
            System.out.println("1.View Menu");
            System.out.println("2 Read customer review");
            System.out.println("3. Place/Cancel order");
            System.out.println("4. Check order status");
            System.out.println("5. Check order history");
            System.out.println("6. Check transaction history");
            System.out.println("7. Provide a review for each order");
            System.out.println("8. Reorder using order history");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt(); // Clear the scanner buffer
            scanner.nextLine(); // Consume the newline left by nextInt()
            switch (choice) {
                case 1:
    try {
        ViewMenu(); // Assuming ViewMenu is the correct method name
                } catch (IOException e) {
                System.out.println("An error occurred while trying to view the menu: " + e.getMessage());
                e.printStackTrace(); // For debugging purposes
                }
                break;

                case 2:
                            try {
                                readCustomerReviews();
                                } catch (IOException e) {
                                System.out.println("An error occurred while reading customer reviews: " + e.getMessage());}
                    break;
                case 3:
                    // placeOrCancelOrder();
                    break;
                case 4:
                    // checkOrderStatus();
                    break;
                case 5:
                    // checkOrderHistory();
                    break;
                case 6:
                    // checkTransactionHistory();
                    break;
                case 7:
                    // provideReviewForOrder();
                    break;
                case 8:
                    // reorderUsingOrderHistory();
                    break;
                case 0:
                    System.out.println("Exiting menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void readCustomerReviews() throws IOException {
    // Load all foods
    Map<String, String> allFoods = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader("Food.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] foodData = line.split(",");
            // Assuming format: Food ID, Account ID (Vendor), Name, Price, Description, Rating, Availability
            allFoods.put(foodData[0].trim(), foodData[2].trim()); // Food ID and Name
        }
    }

    if (allFoods.isEmpty()) {
        System.out.println("No foods found.");
        return;
    }

// Display foods and let the vendor choose one
        System.out.println("Select a food item to view its reviews:");
        allFoods.forEach((foodId, foodName) -> System.out.println(foodId + ": " + foodName));
        System.out.print("Enter Food ID: ");
        String selectedFoodId = scanner.nextLine();

        // Load and display reviews for the selected food
        List<Reviews> reviews = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Reviews.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] reviewData = line.split(",");
                if (reviewData[0].trim().equals(selectedFoodId)) {
                    reviews.add(new Reviews(
                        reviewData[0].trim(), // Food ID
                        Integer.parseInt(reviewData[1].trim()), // Rating
                        reviewData[2].trim(), // Review Message
                        0, // Runner Rating, dummy value as it's not present in the file
                        "", // Runner Review Message, dummy value as it's not present in the file
                        "",
                        ""
                    ));
                }
            }
        }

        if (reviews.isEmpty()) {
            System.out.println("No reviews available for this food item.");
            return;
        }

        // Paginate and display reviews
        int currentPage = 0;
        while (true) {
            int start = currentPage * PAGE_SIZE;
            int end = Math.min((currentPage + 1) * PAGE_SIZE, reviews.size());

            System.out.println("Reviews for " + allFoods.get(selectedFoodId) + " (Page " + (currentPage + 1) + "):");
            for (int i = start; i < end; i++) {
                System.out.println(reviews.get(i));
            }

            if (currentPage > 0) {
                System.out.println("1. Previous Page");
            }
            if (end < reviews.size()) {
                System.out.println("2. Next Page");
            }
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1 && currentPage > 0) {
                currentPage--;
            } else if (choice == 2 && end < reviews.size()) {
                currentPage++;
            } else if (choice == 0) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void ViewMenu() throws IOException {
    // Define a class to hold food details
    class FoodDetails {
        String id;
        String name;
        String description;
        double rating;

        public FoodDetails(String id, String name, String description, double rating) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.rating = rating;
        }

        @Override
        public String toString() {
            return String.format("%s: %s - %s (Rating: %.1f)", id, name, description, rating);
        }
    }

    // Load all foods
    Map<String, FoodDetails> allFoods = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader("Food.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] foodData = line.split(",");
            // Assuming format: Food ID, Vendor ID, Name, Price, Description, Rating, Availability
            double rating = Double.parseDouble(foodData[5].trim());
            allFoods.put(foodData[0].trim(), new FoodDetails(
                foodData[0].trim(),
                foodData[2].trim(),
                foodData[4].trim(),
                rating));
        }
    }

    if (allFoods.isEmpty()) {
        System.out.println("No foods found.");
    } else {
        // Display foods
        System.out.println("Available food items:");
        allFoods.values().forEach(food -> System.out.println(food.toString()));

        // Prompt user to press 0 to return to the main menu
        System.out.println("\nPress 0 to return to the main menu.");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        if (choice == 0) {
            return; // This will exit the readCustomerReviews() method and return to the main menu loop
        } else {
            System.out.println("Invalid choice. Please try again.");
            // Optionally, you can add a loop here to handle invalid inputs
        }
    }
    // Display foods
    System.out.println("Available food items:");
    allFoods.values().forEach(food -> System.out.println(food.toString()));
    
    // If you want to implement functionality for the user to select a food item and view its reviews, you can continue here.
    // Otherwise, if you just wanted to display the food menu, you can return from the method.
}
    
    
    
    @Override
    public void Financial_Dashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
