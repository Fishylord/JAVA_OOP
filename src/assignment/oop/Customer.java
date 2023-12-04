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
            System.out.println("1. Read customer review");
            System.out.println("2. Place/Cancel order");
            System.out.println("3. Check order status");
            System.out.println("4. Check order history");
            System.out.println("5. Check transaction history");
            System.out.println("6. Provide a review for each order");
            System.out.println("7. Reorder using order history");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt(); // Clear the scanner buffer
            scanner.nextLine(); // Consume the newline left by nextInt()
            switch (choice) {
                case 1:
                            try {
                                readCustomerReviews();
                                } catch (IOException e) {
                                System.out.println("An error occurred while reading customer reviews: " + e.getMessage());}
                    break;
                case 2:
                    // placeOrCancelOrder();
                    break;
                case 3:
                    // checkOrderStatus();
                    break;
                case 4:
                    // checkOrderHistory();
                    break;
                case 5:
                    // checkTransactionHistory();
                    break;
                case 6:
                    // provideReviewForOrder();
                    break;
                case 7:
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
    
    @Override
    public void Financial_Dashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
