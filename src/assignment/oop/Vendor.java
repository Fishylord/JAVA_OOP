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
import java.util.Comparator;
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
import java.util.stream.Collectors;

/**
 *REMEMBER TO COPY PASTE IOEXCEPTION FORMAT AND SPAM THEM WHEN ACCESSING FILES. -CCY
 * @author User
 */
public class Vendor extends User {
    private List<Item> items; 
    private final Scanner scanner;
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
                    itemsMenu();
                    break;
                case 2:
                    ordersMenu();
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
                    Financial_Dashboard();
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
                    logout();
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    private void itemsMenu() {
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
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.1");
                    break;
            }
        }
    }
    
    public boolean addItem() {
        String itemId = getNextItemID(); //This is for ID btw
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
        Item newItem = new Item(itemId, getUserID(), name, price, description, 0, availability);
        if (!duplicationCheck(newItem.getName())) {
            items.add(newItem);
            try (FileWriter fw = new FileWriter("Food.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
                out.println(newItem.toString());
            } catch (IOException e) {
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
                    scanner.nextLine(); 
                    switch (editChoice) {
                        case 1:
                            System.out.println("Enter the new name:");
                            String newName = scanner.nextLine();
                            itemToEdit.setName(newName);
                            break;
                        case 2:
                            System.out.println("Enter the new price:");
                            double newPrice = scanner.nextDouble();
                            scanner.nextLine(); 
                            itemToEdit.setPrice(newPrice);
                            break;
                        case 3:
                            System.out.println("Enter the new description:");
                            String newDescription = scanner.nextLine();
                            itemToEdit.setDescription(newDescription);
                            break;
                        case 0:
                            return; 
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
        
    private void displayItemsWithAction(Consumer<List<Item>> pageAction) { //a modular function to display Actions
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
                                itemData[0].trim(), 
                                itemData[1].trim(), 
                                itemData[2].trim(), 
                                Double.parseDouble(itemData[3].trim()), 
                                itemData[4].trim(), 
                                Double.parseDouble(itemData[5].trim()), 
                                isAvailable 
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
                String[] itemData = line.split(","); 
                if (itemData[1].trim().equals(this.getUsername()) && itemData[2].trim().equalsIgnoreCase(itemName)) {
                    return true; 
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from Food.txt.");
            e.printStackTrace();
        }
        return false;
    }

    private void markItemAsUnavailable(String foodId) throws IOException {
        List<Item> items = Item.loadAllItems();
        items.forEach(item -> {
            if (item.getFoodId().equals(foodId) && item.getAccountId().equals(getUserID())) {
                item.setAvailable(false);
            }
        });
        try (PrintWriter out = new PrintWriter(new FileWriter("Food.txt"))) {
            for (Item item : items) {
                out.println(item.toString());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating Food.txt.");
            e.printStackTrace();
        }
    }   

    private void saveItemChanges(Item updatedItem) throws IOException {
        List<Item> items = Item.loadAllItems(); 
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getFoodId().equals(updatedItem.getFoodId())) {
                items.set(i, updatedItem); // Replace the old item with the updated item
                break;
            }
        }

        // Now write the updated list back to the file
        try (PrintWriter out = new PrintWriter(new FileWriter("Food.txt"))) {
            for (Item item : items) {
                out.println(item.toString()); 
            }
        }
    }
    //ALL ORDER STUFF BELOW
    
    private void ordersMenu() {
        int orderChoice = -1;
        while (orderChoice != 0) {
            System.out.println("==== Manage Orders ====");
            System.out.println("1. Accept Order");
            System.out.println("2. Cancel Order");
            System.out.println("3. Update Order");
            System.out.println("0. Return to Main Menu");

            System.out.print("Enter your choice: ");
            orderChoice = scanner.nextInt();
            scanner.nextLine();

            switch (orderChoice) {
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
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    
    private void acceptOrder() { //The 3 blocks of code changes status and process depending on the changed states
        List<Order> modifiedOrders = new ArrayList<>();
        boolean changesMade = displayOrdersWithAction(transactionId -> 
            processOrder(loadVendorOrders(), transactionId, "Pending", "Cooking", modifiedOrders,false) ||
            processOrder(loadVendorOrders(), transactionId,"PendingPhysical","CookingPhysical", modifiedOrders,false));

        if (changesMade) {
            modifiedOrders.forEach(order -> updateBalance(order.getVendorId(), order.getTotalPrice()));
            saveChanges(modifiedOrders);
        }
    }

    private void cancelOrder() {
        List<Order> modifiedOrders = new ArrayList<>();
        boolean changesMade = displayOrdersWithAction(transactionId -> 
            processOrder(loadVendorOrders(), transactionId, "Pending", "Cancelled", modifiedOrders,false) ||
            processOrder(loadVendorOrders(), transactionId, "Cooking", "Cancelled", modifiedOrders,true) ||
            processOrder(loadVendorOrders(), transactionId, "PendingPhyscial","Cancelled", modifiedOrders,false )||
            processOrder(loadVendorOrders(),transactionId, "CookingPhysical", "Cancelled", modifiedOrders,true));
        if (changesMade) {
            saveChanges(modifiedOrders);
        }
}

    private void updateOrder() { 
        List<Order> modifiedOrders = new ArrayList<>();
        boolean changesMade = displayOrdersWithAction(transactionId -> 
            processOrder(loadVendorOrders(), transactionId, "Cooking", "Open", modifiedOrders,false)||
            processOrder(loadVendorOrders(), transactionId, "CookingPhysical", "Delivered", modifiedOrders,false));

        if (changesMade) {
            List<String> deliveryDrivers = loadDeliveryDrivers();
            String availableDriver = findAvailableDriver(deliveryDrivers);

            if (availableDriver != null) {
                modifiedOrders.forEach(order -> {
                    if (order.getStatus().equalsIgnoreCase("Open") && order.getRunnerId().isEmpty()) {
                        order.setRunnerId(availableDriver);
                    }
                });
            } else {
                System.out.println("No available delivery drivers at the moment.");
                modifiedOrders.forEach(this::notifyDriverNotAvailable);
            }

             saveChanges(modifiedOrders);
        }
        
    }

    //Order Function stuff
    private boolean displayOrdersWithAction(Function<String, Boolean> pageAction) {
        List<Order> allVendorOrders = loadVendorOrders();
        int page = 0;
        boolean changesMade = false;

        while (true) {
            int start = page * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, allVendorOrders.size());
            List<Order> pageOrders = allVendorOrders.subList(start, end);

            System.out.println("=========Page " + (page + 1) + "=========");
            pageOrders.forEach(System.out::println);

            String prompt;
            if (page == 0 && end < allVendorOrders.size()) {
                prompt = "Enter the Transaction ID of the order, 1 for next page, or 0 to exit:";
            } else if (page > 0 && end < allVendorOrders.size()) {
                prompt = "Enter the Transaction ID of the order, 1 for next page, 2 for previous page, or 0 to exit:";
            } else if (page > 0) {
                prompt = "Enter the Transaction ID of the order, 2 for previous page, or 0 to exit:";
            } else {
                prompt = "Enter the Transaction ID of the order or 0 to exit:";
            }

            System.out.println(prompt);
            String input = scanner.nextLine();
            if ("0".equals(input)) {
                return changesMade;
            } else if ("1".equals(input) && end < allVendorOrders.size()) {
                page++;
            } else if ("2".equals(input) && page > 0) {
                page--;
            } else {
                boolean actionResult = pageAction.apply(input);
                if (actionResult) {
                    changesMade = true;
                } else {
                    System.out.println("Invalid Transaction ID. Please try again.");
                }
            }
        }
    }

    private boolean processOrder(List<Order> orders, String transactionId, String requiredStatus, String newStatus, List<Order> modifiedOrders,  boolean deductFromVendor) {
        for (Order order : orders) {
            if (order.getTransactionId().equals(transactionId) && order.getStatus().equalsIgnoreCase(requiredStatus)) {
                order.setStatus(newStatus);
                modifiedOrders.add(order);
                System.out.println("Order status updated to " + newStatus + " for Transaction ID: " + transactionId);
                if (newStatus.equals("Canceled")) { //deducts from vedor and if boolean true will deduct vendor as well (if vendor accepts taking the money
                    updateBalance(order.getCustomerId(), order.getTotalPrice()); 
                    if (deductFromVendor) {
                        updateBalance(order.getVendorId(), -order.getTotalPrice()); 
                    }
                }

                
                
                
                return true; //something changed
            }
        }
        System.out.println("Order with Transaction ID " + transactionId + " not found or not in the required status.");
        return false; //nothing changed
    }
    
    private List<Order> loadVendorOrders() {
        List<Order> Orders = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("Transactions.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] orderData = line.split(",");
                if (orderData[6].trim().equals(this.getUserID())) { //check belong to logged in user or not
                    Order order = new Order(
                            orderData[0].trim(), 
                            orderData[1].trim(), 
                            orderData[2].trim(), 
                            Integer.parseInt(orderData[3].trim()), 
                            Double.parseDouble(orderData[4].trim()), 
                            orderData[5].trim(), 
                            orderData[6].trim(), 
                            orderData[7].trim(),  
                            "" //not needed
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
    
    private void saveChanges(List<Order> updatedOrders) {
        List<String> allOrders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Transactions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] orderData = line.split(",");
                String transactionId = orderData[0].trim();

                Order updatedOrder = updatedOrders.stream()
                    .filter(o -> o.getTransactionId().equals(transactionId))
                    .findFirst()
                    .orElse(null);

                if (updatedOrder != null) {
                    // updates the line
                    allOrders.add(updatedOrder.toFileString());
                } else {
                    // Add the existing line as is
                    allOrders.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading Transactions.txt.");
            e.printStackTrace();
            return;
        }

        // Rewrite the file with updated data
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Transactions.txt"))) {
            for (String orderLine : allOrders) {
                bw.write(orderLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to rewrite Transactions.txt.");
            e.printStackTrace();
        }
    }
    
    private void checkOrderHistory() {
        List<Order> Orders = loadVendorOrders();
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
            List<Order> pageOrders = Orders.subList(start, end);

            System.out.println("========= Order History Page " + (page + 1) + " =========");
            if (Orders.isEmpty()) {
                System.out.println("No order history available.");
                return;
            }
            for (Order order : pageOrders) {
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
    
    private List<Order> filterOrdersByPeriod(List<Order> orders, String period) {
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.now();
        int currentYear = today.getYear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Order> filteredOrders = new ArrayList<>();

        for (Order o : orders) {
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
                    // If No filtering
                    break;
            }
        }

        return filteredOrders; // Return the filtered list
    }
    
    
    private void readCustomerReviews() throws IOException {
        Map<String, String> vendorFoods = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Food.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] foodData = line.split(",");
                if (foodData[1].trim().equals(this.getUserID())) {
                    vendorFoods.put(foodData[0].trim(), foodData[2].trim()); // Food ID and Name btw
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

        // Copied display function.
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
    
    private String findAvailableDriver(List<String> allDrivers) {
        Map<String, Boolean> driverAvailability = allDrivers.stream()
            .collect(Collectors.toMap(driver -> driver, driver -> true)); // "set" all drivers as available

        try (BufferedReader br = new BufferedReader(new FileReader("Transactions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] transactionDetails = line.split(",");
                if (("Open".equalsIgnoreCase(transactionDetails[1]) || "Delivering".equalsIgnoreCase(transactionDetails[1])) &&
                    !transactionDetails[8].trim().equals("") && !transactionDetails[8].trim().equalsIgnoreCase("NONE")) {

                    driverAvailability.put(transactionDetails[8].trim(), false); // Mark this driver as busy
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading Transactions.txt.");
            e.printStackTrace();
        }

        for (String driver : allDrivers) {
            if (driverAvailability.getOrDefault(driver, false)) {
                System.out.println(driver);
                return driver; // Return the first available driver not busy
            }
        }

        return null; // returns null if non-available
    }
    
    private List<String> loadDeliveryDrivers() {
        List<String> deliveryDriverIds = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] accountDetails = line.split(",");
                if (accountDetails.length >= 3 && "Delivery".equalsIgnoreCase(accountDetails[2])) {
                    deliveryDriverIds.add(accountDetails[4].trim()); // UserID of the delivery driver
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading Accounts.txt.");
            e.printStackTrace();
        }
        return deliveryDriverIds;
    }
    
    private void updateBalance(String userID, double amount) {
        double currentBalance = updateBalance(userID);
        double newBalance = currentBalance + amount;
        setBalance(userID, newBalance);
    }
    
    private double updateBalance(String userID) { 
        double balance = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[4].trim().equals(userID.trim())) {
                    balance = Double.parseDouble(parts[3].trim());
                    break;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred while reading balances: " + e.getMessage());
        }
        return balance;
    }
    
    private void setBalance(String userID, double newBalance) { //Should have had User been Accounts.java and used getter/setter method
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
    
    //Revenue Dashboard.
    @Override
    public void Financial_Dashboard() {
        double currentBalance = updateBalance(getUserID());
        System.out.println("Current Balance: " + currentBalance);

        List<Order> orders = loadVendorOrders(); 

        System.out.println("Choose revenue period:");
        System.out.println("1. Daily");
        System.out.println("2. Monthly");
        System.out.println("3. Yearly");
        System.out.print("Enter choice (or press Enter for Lifetime Earnings): ");
        String choice = scanner.nextLine();

        double revenue;
        switch (choice) {
            case "1":
                revenue = calculateRevenue(orders, "Daily");
                System.out.println("Daily Revenue: " + revenue);
                break;
            case "2":
                revenue = calculateRevenue(orders, "Monthly");
                System.out.println("Monthly Revenue: " + revenue);
                break;
            case "3":
                revenue = calculateRevenue(orders, "Yearly");
                System.out.println("Yearly Revenue: " + revenue);
                break;
            default:
                revenue = calculateRevenue(orders, "Total");
                System.out.println("Total Revenue: " + revenue);
                break;
        }
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine(); 
    }

    private double calculateRevenue(List<Order> orders, String period) {
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.now();
        int currentYear = today.getYear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return orders.stream()
            .filter(order -> {
                LocalDate orderDate = LocalDate.parse(order.getDate(), formatter);
                switch (period) { //return based on the format
                    case "Daily":
                        return orderDate.equals(today);
                    case "Monthly":
                        return YearMonth.from(orderDate).equals(currentMonth);
                    case "Yearly":
                        return orderDate.getYear() == currentYear;
                    default: // For total all time moneys
                        return true;
                }
            })
            .mapToDouble(Order::getTotalPrice)
            .sum();
    }
    
    //Notification
    
    private void createNotificationForCustomer(String customerId, String transactionId) {
        String notificationMsg = "Your transaction " + transactionId + " has been declined and refunded.";
        int notificationId = getNotificationIDCounter(); // Id getter
        String notificationStatus = "Unread";

        String notificationRecord = customerId + "," + "NOT"+notificationId + "," + notificationMsg + "," + notificationStatus;

        try (FileWriter fw = new FileWriter("Notifications.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(notificationRecord);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to Notifications.txt: " + e.getMessage());
        }
    }
    
    private void notifyDriverNotAvailable(Order order) {
        // Update the Status
        order.setStatus("Delivered");

        // Notification for Customer
        String customerId = order.getCustomerId();
        String notificationMsg = "No available delivery driver. Please pick up your order. Order ID: " + order.getTransactionId();
        int notificationId = getTransactionIDCounter(); //Id Getter (this version only gives numbers)
        String notificationStatus = "Unread";

        String notificationRecord = customerId + "," + "NOT"+notificationId + "," + notificationMsg + "," + notificationStatus;
            
        try (FileWriter fw = new FileWriter("Notifications.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(notificationRecord);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to Notifications.txt: " + e.getMessage());
        }
    }
}
