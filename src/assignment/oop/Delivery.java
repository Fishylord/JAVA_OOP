/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

import java.io.BufferedReader;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.PrintWriter; 
import java.time.LocalDate; 
import java.time.YearMonth;
import java.time.format.DateTimeFormatter; 
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author User
 */
public class Delivery extends User{
    private final Scanner scanner;
    private final double ratePerDelivery = 3.0;
    
    public Delivery(String username, String password, String userID) {
        super(username, password, userID);
        this.scanner = new Scanner(System.in); 
    }
 
    @Override
    public void displayMenu() {
                int choice = -1;
        while (choice != 0) {
            System.out.println("=========Delivery Runner Menu========");
            System.out.println("1. View Task");
            System.out.println("2. Accept Task");
            System.out.println("3. Decline Task");
            System.out.println("4. Update Task Status");
            System.out.println("5. Check Task History");
            System.out.println("6. Read Customer Review");
            System.out.println("7. Revenue Dashboard");
            try {
                if (hasUnreadNotifications()) {
                    System.out.println("8. Notifications (!)");
                } else {
                    System.out.println("8. Notifications");
                }
            } 
            catch (IOException e) {
                System.out.println("Error checking notifications.");
            } //Additional 
            System.out.println("0. Exit");
            
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewTask();
                    break;
                case 2:
                    acceptTask();
                    break;
                case 3:
                    DeclineTask();
                    break;
                case 4:
                    updateTaskStatus();
                    break;
                case 5:
                    checkTaskHistory();
                    break;
                case 6:
                    readCustomerReview();
                    break;
                case 7:
                    revenueDashboard();
                    break;
                case 8:
                {
                    try {
                        this.readNotifications();
                    } catch (IOException ex) {
                        Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
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

private void viewTask() {
    File transactionFile = new File("Transactions.txt");
    try {
        Scanner fileScanner = new Scanner(transactionFile);
        System.out.println("Open Delivery Tasks:");
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] fields = line.split(",");
            // Check the status is Open anot
            if (fields[1].equalsIgnoreCase("Open") && fields[fields.length - 1].equals(this.getUserID())) {
                System.out.println(line); // Print the open transaction
            }
        }
        fileScanner.close();

        System.out.println("\nPress 0 to go back to the menu.");
        int input = scanner.nextInt();
        while (input != 0) {
            System.out.println("Invalid input. Press 0 to go back to the menu.");
            input = scanner.nextInt();
        }
    } catch (FileNotFoundException e) {
        System.err.println("File not found: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
    } finally {
    }
}

  private void acceptTask() {
    List<String> transactions = new ArrayList<>();
    try {
        try (Scanner fileScanner = new Scanner(new File("Transactions.txt"))) {
            while (fileScanner.hasNextLine()) {
                transactions.add(fileScanner.nextLine()); // Add all transactions to the list
            }
        }
    } catch (FileNotFoundException e) {
        System.err.println("File not found: " + e.getMessage());
        return;
    } catch (Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
        return;
    }
    System.out.println("Open Deliveries Available for Acceptance:");
    for (String transaction : transactions) {
        String[] fields = transaction.split(",");
        if (fields[1].equalsIgnoreCase("Open") && fields[fields.length - 1].equals(this.getUserID())) {
            System.out.println(transaction);
        }
    }
    System.out.println("\nEnter the Transaction ID to accept or 0 to go back:");
    String transactionIdToAccept = scanner.nextLine();
    if ("0".equals(transactionIdToAccept)) {System.out.println("Going back to the previous menu...");return;}

    boolean transactionFound = false;
    for (int i = 0; i < transactions.size(); i++) {
        String[] fields = transactions.get(i).split(",");
        if (fields[0].equals(transactionIdToAccept) && fields[1].equalsIgnoreCase("Open") && fields[fields.length - 1].equals(this.getUserID())) {
            fields[1] = "Delivering"; // replace the open to Delivering in the txt file
            transactions.set(i, String.join(",", fields));
            transactionFound = true;
            break;
        }
    }
    // Rewrite it when update that time
    if (transactionFound) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Transactions.txt"))) {
            for (String updatedTransaction : transactions) {
                writer.write(updatedTransaction);
                writer.newLine();
            }
            System.out.println("Task accepted successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    } else {
        System.out.println("Transaction ID not found or task is not open or does not belong to you.");
    }
}
    
private void DeclineTask() {
    List<String> transactions = new ArrayList<>();
    try {
        try (Scanner fileScanner = new Scanner(new File("Transactions.txt"))) {
            while (fileScanner.hasNextLine()) {
                transactions.add(fileScanner.nextLine()); 
            }
        }
    } catch (FileNotFoundException e) {
        System.err.println("File not found: " + e.getMessage());
        return;
    } catch (Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
        return;
    }
    // List out delivery
    System.out.println("Deliveries that can be declined (Accepted and assigned to you):");
    for (String transaction : transactions) {
        String[] fields = transaction.split(",");
        if (fields[1].equalsIgnoreCase("Open") && fields[8].equals(this.getUserID())) {
            System.out.println(transaction);
        }
    }
    //input the transaction ID to decline or 0 to go back
    System.out.print("Enter the Transaction ID of the task to decline or 0 to go back: ");
    String transactionIdToDecline = scanner.nextLine();
    if ("0".equals(transactionIdToDecline)) {
        System.out.println("Returning to the previous menu...");
        return; // Exit
    }
    boolean transactionFound = false; //finder
    for (int i = 0; i < transactions.size(); i++) {
        String[] fields = transactions.get(i).split(",");
        if (fields[0].equals(transactionIdToDecline) && fields[1].equalsIgnoreCase("Open") && fields[8].equals(this.getUserID())) {
            fields[1] = "Open"; 
            fields[8] = "NONE"; 
            transactions.set(i, String.join(",", fields));
            transactionFound = true;
            break;
        }
    }

    if (transactionFound) {
        List<String> allDrivers = loadDeliveryDrivers();
        String newDriverId = findAvailableDriver(allDrivers);
        if (newDriverId != null) {
            for (int i = 0; i < transactions.size(); i++) {
                String[] fields = transactions.get(i).split(",");
                if (fields[0].equals(transactionIdToDecline) && fields[1].equalsIgnoreCase("Open")) {
                    fields[8] = newDriverId; 
                    transactions.set(i, String.join(",", fields));
                    break;
                }
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Transactions.txt"))) {
            for (String updatedTransaction : transactions) {
                writer.write(updatedTransaction);
                writer.newLine();
            }
            System.out.println("Task declined successfully. Reassigned to new driver: " + newDriverId);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    } else {
        System.out.println("Transaction ID not found, not accepted, or not assigned to you.");
    }
}

    private void updateTaskStatus() {
        List<String> transactions = new ArrayList<>();
        try {
            try (Scanner fileScanner = new Scanner(new File("Transactions.txt"))) {
                while (fileScanner.hasNextLine()) {
                    transactions.add(fileScanner.nextLine());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return;
        }
        System.out.println("Your accepted tasks:");
        for (String transaction : transactions) {
            String[] fields = transaction.split(",");
            if (fields[1].equalsIgnoreCase("Delivering") && fields[8].equals(this.getUserID())) {
                System.out.println(transaction);
            }
        }
        System.out.print("Enter the Transaction ID to update or 0 to cancel: ");
        String transactionIdToUpdate = scanner.nextLine();
        if ("0".equals(transactionIdToUpdate)) {
            return; 
        }
        System.out.println("Enter the new status (Delivered/Cancelled): ");
        String newStatus = scanner.nextLine();
        if (!newStatus.equalsIgnoreCase("Delivered") && !newStatus.equalsIgnoreCase("Cancelled")) {
            System.out.println("Invalid status entered.");
            return;
        }
        boolean transactionFound = false;
        for (int i = 0; i < transactions.size(); i++) {
            String[] fields = transactions.get(i).split(",");
            if (fields[0].equals(transactionIdToUpdate) && fields[1].equalsIgnoreCase("Delivering") && fields[8].equals(this.getUserID())) {
                fields[1] = newStatus; 
                transactions.set(i, String.join(",", fields));
                transactionFound = true;
                if (newStatus.equalsIgnoreCase("Delivered")) {
                String customerId = fields[7]; 
                createNotificationForCustomer(customerId, fields[0]); 
                }
                break;
            }
        }
        if (transactionFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Transactions.txt"))) {
                for (String updatedTransaction : transactions) {
                    writer.write(updatedTransaction);
                    writer.newLine();
                }
                System.out.println("Task status updated successfully.");
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        } else {System.out.println("Transaction ID not found or not accepted by you.");}
    }

   private void checkTaskHistory() {
    try {
        File transactionFile = new File("Transactions.txt");
        Scanner fileScanner = new Scanner(transactionFile);
        System.out.println("Your Task History (Completed or Cancelled):");
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] fields = line.split(",");
            if ((fields[1].equalsIgnoreCase("Delivered") || 
            fields[1].equalsIgnoreCase("Completed") || 
            fields[1].equalsIgnoreCase("Cancelled")) && 
            fields[8].equals(this.getUserID())) {
                System.out.println(line); 
            }
        }
        System.out.println("\nPress 0 to go back to the menu.");
        int input = scanner.nextInt();
        while (input != 0) {
            System.out.println("Invalid input. Press 1 to go back to the menu.");
            input = scanner.nextInt();
        }
        fileScanner.close();
    } catch (FileNotFoundException e) {
        System.err.println("File not found: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
    }
}

private void readCustomerReview() {
    String runnerId = this.getUserID(); 
    File reviewFile = new File("Reviews.txt");

    try {
        Scanner fileScanner = new Scanner(reviewFile);
        System.out.println("Customer Reviews for Runner ID: " + runnerId);
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] fields = line.split(",");
            if (fields.length >= 7 && fields[3].equals(runnerId)) {
                String foodId = fields[0];
                String runnerRating = fields[4];
                String runnerDescription = fields[5];
                System.out.println("Food ID: " + foodId + ", Runner Rating: " + runnerRating + ", Runner Review: " + runnerDescription);
            }
        }
        fileScanner.close();
        System.out.println("\nPress 0 to go back to the menu.");
        if (scanner.nextInt() == 0) {
            System.out.println("Returning to the previous menu...");
            return;
        }
        System.out.println("\nPress 0 again to go back to the menu.");
        while (scanner.nextInt() != 0) {
            System.out.println("Invalid input. Press 0 to go back to the menu.");
        }
        System.out.println("Returning to the previous menu...");

    } catch (FileNotFoundException e) {
        System.err.println("Reviews file not found: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
    }
}

private String findAvailableDriver(List<String> allDrivers) {
        Map<String, Boolean> driverAvailability = allDrivers.stream()
            .collect(Collectors.toMap(driver -> driver, driver -> true)); 

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
                return driver; 
            }
        }
        return null; 
    }
    
    private List<String> loadDeliveryDrivers() {
        List<String> deliveryDriverIds = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] accountDetails = line.split(",");
                if (accountDetails.length >= 3 && "Delivery".equalsIgnoreCase(accountDetails[2])) {
                    deliveryDriverIds.add(accountDetails[4].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading Accounts.txt.");
            e.printStackTrace();
        }
        return deliveryDriverIds;
    }
    
private void revenueDashboard() { //take similiar to Cy's
        double currentBalance = getBalance(this.getUserID());
        System.out.println("Current Balance: " + currentBalance);

        List<String[]> transactions = loadRunnerTransactions(this.getUserID());

        System.out.println("Choose revenue period:");
        System.out.println("1. Daily");
        System.out.println("2. Monthly");
        System.out.println("3. Yearly");
        System.out.print("Enter choice (or press Enter for Lifetime Earnings): ");
        String choice = scanner.nextLine();

        double revenue;
        switch (choice) {
            case "1":
                revenue = calculateRevenue(transactions, "Daily");
                System.out.println("Daily Revenue: " + revenue);
                break;
            case "2":
                revenue = calculateRevenue(transactions, "Monthly");
                System.out.println("Monthly Revenue: " + revenue);
                break;
            case "3":
                revenue = calculateRevenue(transactions, "Yearly");
                System.out.println("Yearly Revenue: " + revenue);
                break;
            default:
                revenue = calculateRevenue(transactions, "Total");
                System.out.println("Total Revenue: " + revenue);
                break;
        }
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private double getBalance(String userID) {
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

    private void setBalance(String userID, double newBalance) { //can copy Cy's
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

        private double calculateRevenue(List<String[]> transactions, String period) { //reuse CY's code
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.now();
        int currentYear = today.getYear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int completedDeliveries = 0;
        for (String[] transaction : transactions) {
            if (transaction[1].equalsIgnoreCase("Completed") || transaction[1].equalsIgnoreCase("Delivered")) {
                LocalDate transactionDate = LocalDate.parse(transaction[5], formatter);
                switch (period) {
                    case "Daily":
                        if (transactionDate.equals(today)) {
                            completedDeliveries++;
                        }
                        break;
                    case "Monthly":
                        if (YearMonth.from(transactionDate).equals(currentMonth)) {
                            completedDeliveries++;
                        }
                        break;
                    case "Yearly":
                        if (transactionDate.getYear() == currentYear) {
                            completedDeliveries++;
                        }
                        break;
                    default: // For total revenue
                        completedDeliveries++;
                        break;
                }
            }
        }
        return completedDeliveries * ratePerDelivery;
    }

    private List<String[]> loadRunnerTransactions(String userID) {
        List<String[]> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 9 && parts[8].trim().equals(userID.trim())) {
                    transactions.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading transactions: " + e.getMessage());
        }
        return transactions;
    }
    
   private void createNotificationForCustomer(String customerId, String transactionId) {
    String notificationMsg = "Your order " + transactionId + " has been successfully delivered.";
    int notificationNumber = getNotificationIDCounter(); 
    String notificationId = String.format("NOT%03d", notificationNumber);
    String notificationStatus = "Unread.";
    String notificationRecord = customerId + "," + notificationId + "," + notificationMsg + "," + notificationStatus;
    try (FileWriter fw = new FileWriter("Notifications.txt", true);
         BufferedWriter bw = new BufferedWriter(fw);
         PrintWriter out = new PrintWriter(bw)) {
        out.println(notificationRecord);
    } catch (IOException e) {
        System.out.println("An error occurred while writing to Notifications.txt: " + e.getMessage());
    }
}

    @Override
    public void Financial_Dashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

