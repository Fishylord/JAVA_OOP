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
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            try {
                if (hasUnreadNotifications()) {
                    System.out.println("8. Notifications (!)");
                } else {
                    System.out.println("8. Notifications");
                }
            } catch (IOException e) {
                System.out.println("Error checking notifications.");
            } //Additional Feature.
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
                                e.printStackTrace();} // For debugging purposes
                    break;

                case 2:
                            try {
                                readCustomerReviews();
                                } catch (IOException e) {
                                System.out.println("An error occurred while reading customer reviews: " + e.getMessage());}
                    break;
                case 3:
                    placeOrCancelOrder();
                    break;
                case 4:
                    checkOrderStatus();
                    break;
                case 5:
                    checkOrderHistory();
                    break;
                case 6:
                    checkTransactionReceiptMenu();
                    break;
                case 7:
                    CreateReview();
                    break;
                case 8:
                {
                    try {
                        this.readNotifications();
                    } catch (IOException ex) {
                        Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case 0:
                    System.out.println("Exiting menu...");
                    logout();
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
                if (orderData.length >= 8 && orderData[7].trim().equals(this.getUserID())) {
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
            System.out.println("3. Reorder an Item");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                break;
            } else if (choice == 1 && end < Orders.size()) {
                page++;
            } else if (choice == 2 && page > 0) {
                page--;
            } else if(choice == 3) {
                reorderItem();
                break; 
            }else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void reorderItem() {
        System.out.print("Enter the Transaction ID to reorder or 0 to cancel: ");
        String transactionId = scanner.nextLine();

        if ("0".equals(transactionId)) {
            return; // User cancels the action
        }

        // Load the customer's orders
        List<Transactions> orders = loadCustomerOrders();
        Transactions orderToReorder = orders.stream()
            .filter(order -> order.getTransactionId().equals(transactionId))
            .findFirst()
            .orElse(null);

        if (orderToReorder == null) {
            System.out.println("Transaction not found.");
            return;
        }

        double customerBalance = getCustomerBalance();
        double totalCost = orderToReorder.getTotalPricing() * orderToReorder.getquantity();

        if (customerBalance >= totalCost) {
            int newTransactionId = getTransactionIDCounter();
            String newOrderString = String.format(
                "%d,Pending,%s,%d,%.2f,%s,%s,%s,",
                newTransactionId, orderToReorder.getFoodId(), orderToReorder.getquantity(), totalCost, 
                getDate(), getUserID(), ""
            );

            try (PrintWriter writer = new PrintWriter(new FileWriter("Transactions.txt", true))) {
                writer.println(newOrderString);
                updateCustomerBalance(getUserID(), customerBalance - totalCost);
                System.out.println("Reorder placed successfully! Transaction ID: " + newTransactionId);
            } catch (IOException e) {
                System.out.println("An error occurred while placing the reorder: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Insufficient funds. Please add funds to your account.");
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
        String RunnerID = selectedOrder.getRunnerId();
        
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
        Reviews review = new Reviews(foodId, foodRating, foodReviewMsg, runnerRating, runnerReviewMsg, this.getUserID(), RunnerID); // Assuming runnerId is not available

        // Save the review
        saveReview(review);
        System.out.println(review);
        // Update the transaction status to "Completed"
        updateTransactionStatus(transactionId, "Completed");

        System.out.println("Review submitted successfully.");
    }

    private void saveReview(Reviews review) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reviews.txt", true))) {
            writer.write(review.toFileString());
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
    
    private void placeOrCancelOrder(){
        
        int choice = -1;
        while (choice != 0) {
            System.out.println("=========Place or Cancel Order=========");
            System.out.println("1. Place Order");
            System.out.println("2. Cancel Order");
            System.out.println("0. Exit");
            
            System.out.println("Enter Your Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        
            switch (choice) {
                    case 1:
                        createOrder();                     
                        break;
                        
                    case 2:
                        cancelOrder();
                        break;
            }
        }
    }
    
    private double getFoodPrice(String foodID) {
        double price = 0.0; // Default value if the foodID is not found

        try (BufferedReader reader = new BufferedReader(new FileReader("Food.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].trim().equals(foodID)) {
                    price = Double.parseDouble(parts[3].trim());
                    break; // Stop searching once the foodID is found
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Handle exceptions (e.g., file not found, parsing error)
            System.out.println("An error occurred while reading food prices: " + e.getMessage());
        }

        return price;
    }
    
    private String getVendorID(String foodID) {
        String vendorID = ""; // Default value if the foodID is not found

        try (BufferedReader reader = new BufferedReader(new FileReader("Food.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].trim().equals(foodID)) {
                    vendorID = parts[1].trim();
                    break; // Stop searching once the foodID is found
                }
            }
        } catch (IOException e) {
            // Handle exceptions (e.g., file not found)
            System.out.println("An error occurred while reading vendor IDs: " + e.getMessage());
        }

        return vendorID;
    }
    
    private void createOrder(){
        System.out.print("Enter food ID: ");
        String foodID = scanner.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        
        int dinechoice = -1;
        String status = "Default";
        while (dinechoice == -1) {
            System.out.println("Choose Your Dining Options");
            System.out.println("1. Dine In");
            System.out.println("2. Take Away");
            System.out.println("3. Delivery");
            System.out.println("Enter Your Choice: ");
            dinechoice = scanner.nextInt();
            scanner.nextLine();
            
            switch (dinechoice) {
                    case 1:
                        status = ("PendingPhysical");                     
                        break;
                    case 2:
                        status = ("PendingPhysical");                     
                        break;
                    case 3:
                        status = ("Pending");                     
                        break;
            }
        }
            
        double price = getFoodPrice(foodID);

        String date = getDate();

        String vendorID = getVendorID(foodID);

        int transactionID = getTransactionIDCounter();
        
        String formattedTransactionID = String.format("TRA%03d", transactionID);

        String customerID = getUserID();
        
        String runnerstatus = "NONE";
        
        double totalCost = price * quantity;
        
        double customerBalance = getCustomerBalance();

        if (customerBalance >= totalCost) {
                String orderString = String.format(
                    "%s,%s,%s,%d,%.2f,%s,%s,%s,%s",
                    formattedTransactionID, status, foodID, quantity, totalCost, date, vendorID, customerID, runnerstatus
                );
            try (PrintWriter writer = new PrintWriter(new FileWriter("Transactions.txt", true))) {
                writer.println(orderString);
                System.out.println("Order, " +formattedTransactionID + ", placed successfully!");
            } catch (IOException e) {
                System.out.println("An error occurred while placing the order: " + e.getMessage());
            }
            updateCustomerBalance(customerID, customerBalance - totalCost);
        } else {
            System.out.println("Error: Insufficient funds. Please add funds to your account.");
        }
    }
    
    private void cancelOrder() {
        String customerID = getUserID();

        System.out.print("Enter transaction ID to cancel: ");
        String transactionIDToCancel = scanner.nextLine();

        // Read existing transactions from Transactions.txt, update status, and write back
        try (BufferedReader reader = new BufferedReader(new FileReader("Transactions.txt"));
             PrintWriter writer = new PrintWriter(new FileWriter("TempTransactions.txt"))) {

            String line;
            boolean orderCancelled = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].trim().equals(transactionIDToCancel.trim())) {
                    // Check if the order matches the customer's ID
                    if (parts.length >= 8 && parts[7].trim().equals(customerID.trim())) {
                        // Update the status to "Cancelled"
                        parts[1] = "Cancelled";
                        line = String.join(",", parts);
                        orderCancelled = true;
                    } else {
                        System.out.println("You can only cancel orders associated with your customer ID.");
                    }
                }
                writer.println(line);
            }

            if (orderCancelled) {
                reader.close();
                writer.close();
                if (!new java.io.File("Transactions.txt").delete()) {
                    System.out.println("Error deleting the original file.");
                    return;
                }
                if (!new java.io.File("TempTransactions.txt").renameTo(new java.io.File("Transactions.txt"))) {
                    System.out.println("Error renaming the temporary file.");
                }

                System.out.println("Order cancelled successfully!");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while canceling the order: " + e.getMessage());
        }
    }
    
    private double getCustomerBalance() {
        double balance = 0.0;
        String customerID = getUserID();

        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[4].trim().equals(customerID.trim())) {
                    balance = Double.parseDouble(parts[3].trim());
                    break; // Stop searching once the customerID is found
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred while reading customer balances: " + e.getMessage());
        }

        return balance;
    }
    
    private void updateCustomerBalance(String userID, double newBalance) {
        List<String> accountLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[4].trim().equals(userID.trim())) {
                    parts[3] = String.format("%.2f", newBalance);
                    line = String.join(",", parts);
                }
                accountLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading Accounts.txt: " + e.getMessage());
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("Accounts.txt"))) {
            for (String accountLine : accountLines) {
                writer.println(accountLine);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating Accounts.txt: " + e.getMessage());
        }
    }

    public void checkOrderStatus() {
        List<Transactions> orders = loadCustomerOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        System.out.println("Ongoing Transactions");
        orders.stream()
                .filter(order -> !order.getStatus().equals("Completed"))
                .forEach(order -> {
                    System.out.println("Transaction ID: " + order.getTransactionId());
                    System.out.println("Status: " + order.getStatus());
                });
    }
    
    public void checkTransactionReceiptMenu(){
        int choice = -1;
        while (choice != 0) {
            System.out.println("=========Checking Transactions=========");
            System.out.println("1. Check Transaction");
            System.out.println("2. Check Top Up Receipt");
            System.out.println("0. Exit");
            
            System.out.println("Enter Your Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    checkAllTransactions();
                    break;
                case 2:
                    checkReceipts();
                    break;
            }
        }
    }
    
    public void checkAllTransactions() {
        List<Transactions> allTransactions = loadCustomerOrders();

        if (allTransactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        System.out.println("All Transactions");
        allTransactions.forEach(transaction -> {
            System.out.println("Transaction ID: " + transaction.getTransactionId());
            System.out.println("Food ID: " + transaction.getFoodId());
            System.out.println("Quantity: " + transaction.getquantity());
            System.out.println("Total Price: " + transaction.getTotalPricing());
            System.out.println("Date: " + transaction.getDate());
            System.out.println("----------------------------");
        });
    }    
    
    public void checkReceipts() {
        List<String[]> userReceipts = getUserReceipts(getUserID());

        if (userReceipts.isEmpty()) {
            System.out.println("No receipts available.");
            return;
        }

        System.out.println("Your Receipts");
        userReceipts.forEach(receiptData -> {
            System.out.println("Receipt ID: " + receiptData[0].trim());
            System.out.println("Date: " + receiptData[1].trim());
            System.out.println("Account: " + receiptData[2].trim());
            System.out.println("Current Balance: " + Double.valueOf(receiptData[3].trim()));
            System.out.println("Top-Up Amount: " + Double.valueOf(receiptData[4].trim()));
            System.out.println("Final Balance: " + Double.valueOf(receiptData[5].trim()));
            System.out.println("----------------------------");
        });
    }

    private List<String[]> getUserReceipts(String userID) {
        List<String[]> userReceipts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("AllReceipts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] receiptData = line.split(",");
                // Assuming the format is: receiptId, date, account, current balance, top up amount, final balance
                if (receiptData.length >= 6 && receiptData[2].trim().equals(userID)) {
                    userReceipts.add(receiptData);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading receipts: " + e.getMessage());
        }

        return userReceipts;
    }   
}
