/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author User
 */
public class Delivery extends User{
    private final Scanner scanner;
    
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
            System.out.println("0. Exit");
            
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

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
                case 0:
                    System.out.println("Exiting delivery runner menu...");
                    return; // Exit the method
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // Method stubs for the functionality
    private void viewTask() {
        // Implementation
        File transactionFile = new File("Transactions.txt");
    try {
        Scanner fileScanner = new Scanner(transactionFile);
        System.out.println("Open Delivery Tasks:");
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] fields = line.split(",");
            // Check if the transaction status is "Open"
            if (fields[1].equalsIgnoreCase("Open")) {
                System.out.println(line); // Print the open transaction
            }
        }
        fileScanner.close();

        // Prompt to go back to the menu
        System.out.println("\nPress 0 to go back to the menu.");
        int input = scanner.nextInt();
        while (input != 0) {
            System.out.println("Invalid input. Press 0 to go back to the menu.");
            input = scanner.nextInt();
        }
        // If 1 is pressed, displayMenu() will be called from the main menu switch case
        
    } catch (FileNotFoundException e) {
        System.err.println("File not found: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
    } finally {
        // It's good practice to use try-with-resources or to close the scanner in a finally block.
        // Since we are using the scanner throughout the class, we should not close it here.
        // If this is the only method using the scanner, you could close it here instead of in the displayMenu method.
    }
}

  private void acceptTask() {
    // First, read and store all the transactions
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

    // List out open deliveries with runner ID as the current user
    System.out.println("Open Deliveries Available for Acceptance:");
    for (String transaction : transactions) {
        String[] fields = transaction.split(",");
        if (fields[1].equalsIgnoreCase("Open") && fields[fields.length - 1].equals(this.getUserID())) {
            System.out.println(transaction); // Print the open and available transaction
        }
    }

    // Add a prompt to accept an order or go back
    System.out.println("\nEnter the Transaction ID to accept or 0 to go back:");
    String transactionIdToAccept = scanner.nextLine();

    // Check if the user wants to go back
    if ("0".equals(transactionIdToAccept)) {
        System.out.println("Going back to the previous menu...");
        return; // Exits the method, effectively going back
    }

    // Update the transaction status if it's valid
    boolean transactionFound = false;
    for (int i = 0; i < transactions.size(); i++) {
        String[] fields = transactions.get(i).split(",");
        if (fields[0].equals(transactionIdToAccept) && fields[1].equalsIgnoreCase("Open") && fields[fields.length - 1].equals(this.getUserID())) {
            fields[1] = "Delivering"; // Update the status to 'Delivering'
            transactions.set(i, String.join(",", fields));
            transactionFound = true;
            break;
        }
    }

    // Rewrite the Transactions.txt file if a transaction was updated
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
    // First, read and store all the transactions
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

    // List out deliveries that can be declined (Accepted and assigned to this runner)
    System.out.println("Deliveries that can be declined (Accepted and assigned to you):");
    for (String transaction : transactions) {
        String[] fields = transaction.split(",");
        if (fields[1].equalsIgnoreCase("Delivering") && fields[8].equals(this.getUserID())) {
            System.out.println(transaction);
        }
    }

    // Ask the user to input the transaction ID to decline or 0 to go back
    System.out.print("Enter the Transaction ID of the task to decline or 0 to go back: ");
    String transactionIdToDecline = scanner.nextLine();

    // Check if the user wants to go back
    if ("0".equals(transactionIdToDecline)) {
        System.out.println("Returning to the previous menu...");
        return; // Exits the method
    }

    // Update the transaction status if it's valid
    boolean transactionFound = false;
    for (int i = 0; i < transactions.size(); i++) {
        String[] fields = transactions.get(i).split(",");
        // Check if the transaction is Accepted and assigned to the current runner
        if (fields[0].equals(transactionIdToDecline) && fields[1].equalsIgnoreCase("Delivering") && fields[8].equals(this.getUserID())) {
            fields[1] = "Open"; // Update the status back to 'Open'
            fields[8] = "NONE"; // Clear the runner's ID
            transactions.set(i, String.join(",", fields));
            transactionFound = true;
            break;
        }
    }

    // Write the updated transactions back to the file if a transaction was found and updated
    if (transactionFound) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Transactions.txt"))) {
            for (String updatedTransaction : transactions) {
                writer.write(updatedTransaction);
                writer.newLine();
            }
            System.out.println("Task declined successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    } else {
        System.out.println("Transaction ID not found, not accepted, or not assigned to you.");
    }
}


    private void updateTaskStatus() {
    // First, read all the transactions into a list
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

    // Display accepted tasks for this runner
    System.out.println("Your accepted tasks:");
    for (String transaction : transactions) {
        String[] fields = transaction.split(",");
        if (fields[1].equalsIgnoreCase("Delivering") && fields[8].equals(this.getUserID())) {
            System.out.println(transaction);
        }
    }

    // Ask the runner which task to update
    System.out.print("Enter the Transaction ID to update or 0 to cancel: ");
    String transactionIdToUpdate = scanner.nextLine();

    if ("0".equals(transactionIdToUpdate)) {
        return; // Early exit if the runner decides to cancel the operation
    }

    // Ask for the new status
    System.out.println("Enter the new status (Delivered/Cancelled): ");
    String newStatus = scanner.nextLine();

    // Validate the new status
    if (!newStatus.equalsIgnoreCase("Delivered") && !newStatus.equalsIgnoreCase("Cancelled")) {
        System.out.println("Invalid status entered.");
        return;
    }

    // Update the task status if valid
    boolean transactionFound = false;
    for (int i = 0; i < transactions.size(); i++) {
        String[] fields = transactions.get(i).split(",");
        if (fields[0].equals(transactionIdToUpdate) && fields[1].equalsIgnoreCase("Delivering") && fields[8].equals(this.getUserID())) {
            fields[1] = newStatus; // Update the status to 'Completed' or 'Cancelled'
            transactions.set(i, String.join(",", fields));
            transactionFound = true;
            break;
        }
    }

    // Rewrite the Transactions.txt file if a transaction was updated
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
    } else {
        System.out.println("Transaction ID not found or not accepted by you.");
    }
}

   private void checkTaskHistory() {
    try {
        File transactionFile = new File("Transactions.txt");
        Scanner fileScanner = new Scanner(transactionFile);

        System.out.println("Your Task History (Completed or Cancelled):");
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] fields = line.split(",");

            // Check if the transaction status is either Completed or Cancelled and matches the runner's ID
            if ((fields[1].equalsIgnoreCase("Delivered") || fields[1].equalsIgnoreCase("Cancelled")) && fields[8].equals(this.getUserID())) {
                System.out.println(line); // Print the transaction
            }
        }

        // Add a prompt to go back to the menu
        System.out.println("\nPress 0 to go back to the menu.");
        int input = scanner.nextInt();
        while (input != 0) {
            System.out.println("Invalid input. Press 1 to go back to the menu.");
            input = scanner.nextInt();
        }
        // If 1 is pressed, displayMenu() will be called from the main menu switch case

        fileScanner.close();

    } catch (FileNotFoundException e) {
        System.err.println("File not found: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
    }
}

     private void readCustomerReview() {
        String runnerId = this.getUserID(); // Assuming getUserID() returns the logged-in runner's ID
        File reviewFile = new File("Reviews.txt");
        
        try {
            Scanner fileScanner = new Scanner(reviewFile);
            System.out.println("Customer Reviews for Runner ID: " + runnerId);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] fields = line.split(",");
                // Check if the review is for the current runner
                if (fields[3].equals(runnerId)) {
                    String foodId = fields[0];
                    String runnerRating = fields[4];
                    String runnerDescription = fields[5];
                    System.out.println("Food ID: " + foodId + ", Runner Rating: " + runnerRating + ", Runner Review: " + runnerDescription);
                }
            }
            fileScanner.close();

            // Add a prompt to go back to the menu
            System.out.println("\nPress 0 to go back to the menu.");
            if (scanner.nextInt() == 0) {
                System.out.println("Returning to the previous menu...");
                // Assuming displayMenu() will be called from the main menu switch case
            }

        } catch (FileNotFoundException e) {
            System.err.println("Reviews file not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }


private void revenueDashboard() {
        try {
            String runnerId = this.getUserID();
            double totalEarnings = calculateTotalEarnings(runnerId);

            // Update and display new salary
            updateSalaryInAccounts(runnerId, totalEarnings);
            System.out.println("Total Earnings for Runner ID " + runnerId + ": " + String.format("%.2f", totalEarnings));

            // Add a prompt to go back to the menu
            System.out.println("\nPress 0 to go back to the menu.");
            if (scanner.nextInt() == 0) {
                System.out.println("Returning to the previous menu...");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    private double calculateTotalEarnings(String runnerId) {
        double earnings = 0.0;
        File transactionFile = new File("Transactions.txt");
        try (Scanner fileScanner = new Scanner(transactionFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] fields = line.split(",");
                if (fields[1].equalsIgnoreCase("Delivered") && fields[8].equals(runnerId)) {
                    earnings += 3.00; // Add 3.00 per completed delivery
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Transactions file not found: " + e.getMessage());
        }
        return earnings;
    }

   private void updateSalaryInAccounts(String runnerId, double additionalEarnings) {
    List<String> accounts = new ArrayList<>();
    File accountsFile = new File("Accounts.txt");
    try (Scanner fileScanner = new Scanner(accountsFile)) {
        while (fileScanner.hasNextLine()) {
            accounts.add(fileScanner.nextLine());
        }
    } catch (FileNotFoundException e) {
        System.err.println("Accounts file not found: " + e.getMessage());
        return;
    }

    try (FileWriter writer = new FileWriter(accountsFile)) {
        for (String account : accounts) {
            String[] fields = account.split(",");
            if (fields[4].equals(runnerId)) {
                // Reset the current salary to 0 before adding the additional earnings
                fields[3] = String.format("%.2f", additionalEarnings);
                account = String.join(",", fields);
            }
            writer.write(account + System.lineSeparator());
        }
    } catch (IOException e) {
        System.err.println("Error writing to Accounts file: " + e.getMessage());
    }
}

    
    @Override
    public void Financial_Dashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
