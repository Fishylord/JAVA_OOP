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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Vendor extends User {
    private List<Item> items; 
    private Scanner scanner;
    private List<Order> orders; 
    
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
            System.out.println("2. Accept/Cancle/Update Order");
            System.out.println("3. Check Order History");
            System.out.println("4. Read Customer Review");
            System.out.println("5. Revenue Dashboard");
            try {
                if (hasUnreadNotifications()) {
                    System.out.println("6. Notifications (!)");
                } else {
                    System.out.println("6. Notifications");
                }
            } catch (IOException e) {
                System.out.println("Error checking notifications.");
            } //Additional Feature.
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
                                removeItem();
                                break;
                            case 4:
                                displayItems();
                                break;
                            case 0:
                                displayMenu();
                                return; 
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    }
                    break;
                case 2:
                    int OrderChoice = -1;
                    while (OrderChoice != 0) {
                        System.out.println("==== Manage Items ====");
                        System.out.println("1. Accept Order");
                        System.out.println("2. Cancle Order");
                        System.out.println("3. Update Order");
                        System.out.println("0. Return to Main Menu");

                        System.out.print("Enter your choice: ");
                        manageChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (manageChoice) {
                            case 1:
                                acceptOrder();
                                break;
                            case 2:
                                cancelOrder();
                                break;
                            case 3:
                                updateOrder();
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
                case 3:
                    checkOrderHistory();
                    break;
                case 4:
                    try {
                        readCustomerReviews();
                    } catch (IOException ex) {
                        Logger.getLogger(Vendor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

                case 5:
//                    Revenue Dashboard
                    break;
                case 6:
                {
                    try {
                        this.readNotifications();
                    } catch (IOException ex) {
                        Logger.getLogger(Vendor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
//                    
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
            try (FileWriter fw = new FileWriter("Food.txt", true);
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
    
    public void removeItem() {
        displayItemsWithAction(pageItems -> {
            System.out.println("Enter the Food ID of the item to delete or 0 to cancel:");
            String foodId = scanner.nextLine();

            if (!"0".equals(foodId)) {
                Optional<Item> itemToBeRemoved = pageItems.stream()
                    .filter(item -> item.getFoodId().equals(foodId) && item.getAccountId().equals(getUserID()))
                    .findFirst();

                if (itemToBeRemoved.isPresent()) {
                    try {
                        markItemAsUnavailable(foodId);
                        System.out.println("Item with Food ID " + foodId + " has been marked as unavailable.");
                    } catch (IOException e) {
                        System.out.println("An error occurred while updating the item's availability.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Item with the specified Food ID does not exist or does not belong to you.");
                }
            }
        });
    }
    
    private void editItem() {
        displayItemsWithAction(pageItems -> {
            System.out.println("Enter the Food ID of the item to edit or 0 to cancel:");
            String foodId = scanner.nextLine();

            if (!"0".equals(foodId)) {
                Optional<Item> optionalItem = pageItems.stream()
                        .filter(item -> item.getFoodId().equals(foodId) && item.getAccountId().equals(getUserID()))
                        .findFirst();

                if (optionalItem.isPresent()) {
                    Item itemToEdit = optionalItem.get();
                    System.out.println("Selected item: " + itemToEdit);

                    System.out.println("What would you like to edit?");
                    System.out.println("1. Name");
                    System.out.println("2. Price");
                    System.out.println("3. Description");
                    System.out.println("0. Cancel");

                    int editChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over

                    switch (editChoice) {
                        case 1:
                            System.out.println("Enter the new name:");
                            String newName = scanner.nextLine();
                            itemToEdit.setName(newName);
                            break;
                        case 2:
                            System.out.println("Enter the new price:");
                            double newPrice = scanner.nextDouble();
                            scanner.nextLine(); // Consume newline left-over
                            itemToEdit.setPrice(newPrice);
                            break;
                        case 3:
                            System.out.println("Enter the new description:");
                            String newDescription = scanner.nextLine();
                            itemToEdit.setDescription(newDescription);
                            break;
                        case 0:
                            return; // Exit without making changes
                        default:
                            System.out.println("Invalid choice. No changes made.");
                            return;
                    }

                    // Save changes back to the file
                    try {
                        saveItemChanges(itemToEdit);
                        System.out.println("Item updated successfully.");
                    } catch (IOException e) {
                        System.out.println("An error occurred while updating the item.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Item with the specified Food ID does not exist or does not belong to you.");
                }
            }
        });
    }
    
    public void displayItems() {
        displayItemsWithAction(pageItems -> {
            // This is an empty action; you just display the items and don't do anything else.
        });
    }
        
    private void displayItemsWithAction(Consumer<List<Item>> pageAction) {
    List<Item> allAvailableItems = loadAvailableItems();
    int page = 0;

    while (true) {
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, allAvailableItems.size());
        List<Item> pageItems = allAvailableItems.subList(start, end);

        // Display the current page of items
        System.out.println("=========Page " + (page + 1) + "=========");
        for (Item item : pageItems) {
            System.out.println(item);
        }
        System.out.println("=========Page " + (page + 1) + "=========");
        // Perform the custom action on the current page of items
        pageAction.accept(pageItems);

        // Pagination controls
        System.out.println("0. Exit");
        if (end < allAvailableItems.size()) {
            System.out.println("1. Next");
        }
        if (page > 0) {
            System.out.println("2. Previous");
        }

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            break; // Exit the loop
        } else if (choice == 1 && end < allAvailableItems.size()) {
            page++; // Next page
        } else if (choice == 2 && page > 0) {
            page--; // Previous page
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
}

    private List<Item> loadAvailableItems() {
        List<Item> availableItems = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("Food.txt"))) {
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

    private void markItemAsUnavailable(String foodId) throws IOException {
        // Load all items
        List<Item> items = Item.loadAllItems();

        // Find and update the item
        items.forEach(item -> {
            if (item.getFoodId().equals(foodId) && item.getAccountId().equals(getUserID())) {
                item.setAvailable(false);
            }
        });

        // Write the updated list back to the file
        try (PrintWriter out = new PrintWriter(new FileWriter("Food.txt"))) {
            for (Item item : items) {
                out.println(item.toString());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating Food.txt.");
            e.printStackTrace();
        }
    }   

    
    @Override
    public void Financial_Dashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//ALL ORDER STUFF BELOW
    
    private void saveItemChanges(Item updatedItem) throws IOException {
        List<Item> items = Item.loadAllItems(); // Assuming this method loads all items, not just the available ones
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getFoodId().equals(updatedItem.getFoodId())) {
                items.set(i, updatedItem); // Replace the old item with the updated item
                break;
            }
        }

        // Now write the updated list back to the file
        try (PrintWriter out = new PrintWriter(new FileWriter("Food.txt"))) {
            for (Item item : items) {
                out.println(item.toString()); // Ensure you have a method that formats the item for file writing
            }
        }
    }

    private void acceptOrder() {
        boolean changesMade = displayOrdersWithAction(pageOrders -> {
            System.out.println("Enter the Transaction ID of the order to accept or 0 to cancel:");
            String transactionId = scanner.nextLine();

            if ("0".equals(transactionId)) {
                return false;
            }

            for (Order order : pageOrders) {
                if (order.getTransactionId().equals(transactionId)) {
                    order.setStatus("Accepted");
                    System.out.println("Order accepted.");
                    return true; // Change was made
                }
            }
            System.out.println("Order not found.");
            return false; // No change was made
        });

        if (changesMade) {
            try {
                Order.saveOrders(loadVendorOrders(), "path_to_orders_file.txt");
            } catch (IOException e) {
                System.out.println("Failed to save the order changes: " + e.getMessage());
            }
        }
    }

    private void cancelOrder() {
        boolean changesMade = displayOrdersWithAction(pageOrders -> {
            System.out.println("Enter the Transaction ID of the order to cancel or 0 to cancel:");
            String transactionId = scanner.nextLine();

            if ("0".equals(transactionId)) {
                return false;
            }

            for (Order order : pageOrders) {
                if (order.getTransactionId().equals(transactionId)) {
                    order.setStatus("Canceled");
                    System.out.println("Order canceled.");
                    return true; // Change was made
                }
            }
            System.out.println("Order not found.");
            return false; // No change was made
        });

        if (changesMade) {
            try {
                Order.saveOrders(loadVendorOrders(), "path_to_orders_file.txt");
            } catch (IOException e) {
                System.out.println("Failed to save the order changes: " + e.getMessage());
            }
        }
    }

    private void updateOrder() {
        boolean changesMade = displayOrdersWithAction(pageOrders -> {
            System.out.println("Enter the Transaction ID of the order to update or 0 to cancel:");
            String transactionId = scanner.nextLine();

            if ("0".equals(transactionId)) {
                return false;
            }

            for (Order order : pageOrders) {
                if (order.getTransactionId().equals(transactionId)) {
                    System.out.println("Enter the new status of the order:");
                    String newStatus = scanner.nextLine();
                    order.setStatus(newStatus);
                    System.out.println("Order status updated to " + newStatus);
                    return true; // Change was made
                }
            }
            System.out.println("Order not found.");
            return false; // No change was made
        });

        if (changesMade) {
            try {
                Order.saveOrders(loadVendorOrders(), "path_to_orders_file.txt");
            } catch (IOException e) {
                System.out.println("Failed to save the order changes: " + e.getMessage());
            }
        }
    }
    
//Order Function stuff
    private boolean displayOrdersWithAction(Function<List<Order>, Boolean> pageAction) {
        List<Order> allVendorOrders = loadVendorOrders();
        int page = 0;
        boolean changesMade = false;

        while (true) {
            int start = page * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, allVendorOrders.size());
            List<Order> pageOrders = allVendorOrders.subList(start, end);

            System.out.println("=========Page " + (page + 1) + "=========");
            for (Order order : pageOrders) {
                System.out.println(order);
            }
            System.out.println("=========Page " + (page + 1) + "=========");

            changesMade = pageAction.apply(pageOrders) || changesMade;

            System.out.println("0. Exit");
            if (end < allVendorOrders.size()) {
                System.out.println("1. Next");
            }
            if (page > 0) {
                System.out.println("2. Previous");
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                break;
            } else if (choice == 1 && end < allVendorOrders.size()) {
                page++;
            } else if (choice == 2 && page > 0) {
                page--;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        return changesMade;
    }


    private List<Order> loadVendorOrders() {
        List<Order> vendorOrders = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("Transactions.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] orderData = line.split(",");
                // Assuming the format is: transactionId, status, foodId, quantity, totalPrice, date, vendorId, customerId
                if (orderData[6].trim().equals(this.getUserID())) { // Check if the order belongs to this vendor
                    Order order = new Order(
                            orderData[0].trim(), // Transaction ID
                            orderData[1].trim(), // Status
                            orderData[2].trim(), // Food ID
                            Integer.parseInt(orderData[3].trim()), // Quantity
                            Double.parseDouble(orderData[4].trim()), // Total Price
                            orderData[5].trim(), // Date
                            orderData[6].trim(), // Vendor ID
                            orderData[7].trim()  // Customer ID
                    );
                    vendorOrders.add(order);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the orders file.");
            e.printStackTrace();
        }
        return vendorOrders;
    }
    
    private void checkOrderHistory() {
        List<Order> vendorOrders = loadVendorOrders();
        if (vendorOrders.isEmpty()) {
            System.out.println("No order history available.");
            return;
        }

        int page = 0;
        while (true) {
            int start = page * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, vendorOrders.size());
            List<Order> pageOrders = vendorOrders.subList(start, end);

            System.out.println("========= Order History Page " + (page + 1) + " =========");
            for (Order order : pageOrders) {
                System.out.println(order);
            }

            if (end < vendorOrders.size()) {
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
            } else if (choice == 1 && end < vendorOrders.size()) {
                page++;
            } else if (choice == 2 && page > 0) {
                page--;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void readCustomerReviews() throws IOException {
        // Load foods belonging to the vendor
        Map<String, String> vendorFoods = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Food.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] foodData = line.split(",");
                // Assuming format: Food ID, Account ID (Vendor), Name, Price, Description, Rating, Availability
                if (foodData[1].trim().equals(this.getUserID())) {
                    vendorFoods.put(foodData[0].trim(), foodData[2].trim()); // Food ID and Name
                }
            }
        }

        if (vendorFoods.isEmpty()) {
            System.out.println("No foods found for this vendor.");
            return;
        }

        // Display foods and let the vendor choose one
        System.out.println("Select a food item to view its reviews:");
        vendorFoods.forEach((foodId, foodName) -> System.out.println(foodId + ": " + foodName));
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

            System.out.println("Reviews for " + vendorFoods.get(selectedFoodId) + " (Page " + (currentPage + 1) + "):");
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
}
