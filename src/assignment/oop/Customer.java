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
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
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
            System.out.println("7. Leave a Review");
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
                    checkOrderHistory();
                    break;
                case 6:
                    // checkTransactionHistory();
                    break;
                case 7:
                    CreateReview();
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
    
    private List<Transactions> loadCustomerOrders() {
        List<Transactions> Orders = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("Transactions.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] orderData = line.split(",");
                // Assuming the format is: transactionId, status, foodId, quantity, totalPrice, date, vendorId, customerId
                if (orderData[7].trim().equals(this.getUserID())) { // Check if the order belongs to this vendor
                    Transactions order = new Transactions(
                            orderData[0].trim(), // Transaction ID
                            orderData[1].trim(), // Status
                            orderData[2].trim(), // Food ID
                            Integer.parseInt(orderData[3].trim()), // Quantity
                            Double.parseDouble(orderData[4].trim()), // Total Price
                            orderData[5].trim(), // Date
                            "",
                            ""
                    );
                    Orders.add(order);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the orders file.");
            e.printStackTrace();
        }
        return Orders;
    }
    
    private void checkOrderHistory() {
        List<Transactions> Orders = loadCustomerOrders();
        if (Orders.isEmpty()) {
            System.out.println("No order history available.");
            return;
        }

        int page = 0;
        System.out.println("Choose sorting option:");
        System.out.println("1. Daily");
        System.out.println("2. Monthly");
        System.out.println("3. Yearly");
        System.out.print("Enter choice (or press Enter for no sorting): ");
        String sortingChoice = scanner.nextLine();
        // Perform sorting based on the choice
        switch (sortingChoice) {
            case "1":
                Orders = filterOrdersByPeriod(Orders, "Daily");
                break;
            case "2":
                Orders = filterOrdersByPeriod(Orders, "Monthly");
                break;
            case "3":
                Orders = filterOrdersByPeriod(Orders, "Yearly");
                break;
            default:
                break; //just shows normal
        }
        while (true) {
            int start = page * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, Orders.size());
            List<Transactions> pageOrders = Orders.subList(start, end);

            System.out.println("========= Order History Page " + (page + 1) + " =========");
            if (Orders.isEmpty()) {
                System.out.println("No order history available.");
                return;
            }
            for (Transactions order : pageOrders) {
                System.out.println(order);
            }

            if (end < Orders.size()) {
                System.out.println("1. Next Page");
            }
            if (page > 0) {
                System.out.println("2. Previous Page");
            }
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                break;
            } else if (choice == 1 && end < Orders.size()) {
                page++;
            } else if (choice == 2 && page > 0) {
                page--;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private List<Transactions> filterOrdersByPeriod(List<Transactions> orders, String period) {
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.now();
        int currentYear = today.getYear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Transactions> filteredOrders = new ArrayList<>();

        for (Transactions o : orders) {
            LocalDate orderDate = LocalDate.parse(o.getDate(), formatter);
            switch (period) {
                case "Daily":
                    if (orderDate.equals(today)) {
                        filteredOrders.add(o);
                    }
                    break;
                case "Monthly":
                    if (YearMonth.from(orderDate).equals(currentMonth)) {
                        filteredOrders.add(o);
                    }
                    break;
                case "Yearly":
                    if (orderDate.getYear() == currentYear) {
                        filteredOrders.add(o);
                    }
                    break;
                default:
                    // No filtering
                    break;
            }
        }

        return filteredOrders; // Return the filtered list
    }
    
    private void CreateReview(){
        List<Transactions> orders = loadCustomerOrders();

        List<Transactions> deliveredOrders = orders.stream()
            .filter(order -> order.getStatus().equalsIgnoreCase("Delivered"))
            .collect(Collectors.toList());

        if (deliveredOrders.isEmpty()) {
            System.out.println("No delivered orders available for review.");
            return;
        }

        int page = 0;
        while (true) {
            int start = page * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, deliveredOrders.size());
            List<Transactions> pageOrders = deliveredOrders.subList(start, end);

            System.out.println("========= Delivered Orders Page " + (page + 1) + " =========");
            for (int i = 0; i < pageOrders.size(); i++) {
                System.out.println((i + 1) + ". " + pageOrders.get(i));
            }

            // Show "Next Page" option only if there are more pages
            if (end < deliveredOrders.size()) {
                System.out.println("1. Next Page");
            }
            // Show "Previous Page" option only if not on the first page
            if (page > 0) {
                System.out.println("2. Previous Page");
            }
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                break;
            } else if (choice == 1 && end < deliveredOrders.size()) {
                page++;
            } else if (choice == 2 && page > 0) {
                page--;
            } else if (choice > 0 && choice <= pageOrders.size()) {
                // Call leaveReview method for the selected order
                leaveReview(pageOrders.get(choice - 1));
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
   private void leaveReview(Transactions selectedOrder) {
        String foodId = selectedOrder.getFoodId();
        String transactionId = selectedOrder.getTransactionId();

        // Prompt for food rating and review
        System.out.print("Enter your rating for the food (1-5): ");
        double foodRating = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter your review message for the food: ");
        String foodReviewMsg = scanner.nextLine();

        // Prompt for runner rating and review
        System.out.print("Enter your rating for the runner (1-5): ");
        int runnerRating = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter your review message for the runner: ");
        String runnerReviewMsg = scanner.nextLine();

        // Create the review
        Reviews review = new Reviews(foodId, foodRating, foodReviewMsg, runnerRating, runnerReviewMsg, this.getUserID(), ""); // Assuming runnerId is not available

        // Save the review
        saveReview(review);

        // Update the transaction status to "Completed"
        updateTransactionStatus(transactionId, "Completed");

        System.out.println("Review submitted successfully.");
    }

    private void saveReview(Reviews review) {
        // Implement logic to save the review to a file or database
        // Example: Append the review to a file called Reviews.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reviews.txt", true))) {
            writer.write(review.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the review.");
            e.printStackTrace();
        }
    }

    private void updateTransactionStatus(String transactionId, String newStatus) {
        // Implement logic to update the transaction status in Transactions.txt
        // Read the transactions, update the status of the matching transaction, and write back
        List<String> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(transactionId + ",")) {
                    // Replace the status in the transaction line
                    transactions.add(line.replaceFirst("Delivered", newStatus));
                } else {
                    transactions.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading transactions.");
            e.printStackTrace();
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Transactions.txt"))) {
            for (String transaction : transactions) {
                writer.write(transaction);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating transactions.");
            e.printStackTrace();
        }
    }
    
    @Override
    public void Financial_Dashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
