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
        System.out.println("\nPress 1 to go back to the menu.");
        int input = scanner.nextInt();
        while (input != 1) {
            System.out.println("Invalid input. Press 1 to go back to the menu.");
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

    // Ask the user to input the transaction ID
    System.out.print("Enter the Transaction ID to accept: ");
    String transactionIdToAccept = scanner.nextLine();

    // Update the transaction status if it's valid
    boolean transactionFound = false;
    for (int i = 0; i < transactions.size(); i++) {
        String[] fields = transactions.get(i).split(",");
        if (fields[0].equals(transactionIdToAccept) && fields[1].equalsIgnoreCase("Open")) {
            fields[1] = "Pending"; // Update the status to 'Pending'
            fields[fields.length - 1] = this.getUserID(); // Set the runner ID in the last field
            transactions.set(i, String.join(",", fields));
            transactionFound = true;
            break;
        }
    }

    // If the transaction was found and updated, rewrite the Transactions.txt file
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
        System.out.println("Transaction ID not found or task is not open.");
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

    // Ask the user to input the transaction ID to decline
    System.out.print("Enter the Transaction ID of the pending task to decline: ");
    String transactionIdToDecline = scanner.nextLine();

    // Update the transaction status if it's valid
    boolean transactionFound = false;
    for (int i = 0; i < transactions.size(); i++) {
    String[] fields = transactions.get(i).split(",");
    // Check if the transaction is Pending and assigned to the current runner
    if (fields[0].equals(transactionIdToDecline) && fields[1].equalsIgnoreCase("Pending") && fields[8].equals(this.getUserID())) {
        fields[1] = "Open"; // Update the status back to 'Open'
        fields[8] = "NONE"; // Clear the runner's ID
        transactions.set(i, String.join(",", fields));
        transactionFound = true;
        break;
    }
}

    // If the transaction was found and updated, rewrite the Transactions.txt file
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
        System.out.println("Transaction ID not found, not pending, or not assigned to you.");
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
        if (fields[1].equalsIgnoreCase("Accepted") && fields[8].equals(this.getUserID())) {
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
    System.out.println("Enter the new status (Completed/Cancelled): ");
    String newStatus = scanner.nextLine();

    // Validate the new status
    if (!newStatus.equalsIgnoreCase("Completed") && !newStatus.equalsIgnoreCase("Cancelled")) {
        System.out.println("Invalid status entered.");
        return;
    }

    // Update the task status if valid
    boolean transactionFound = false;
    for (int i = 0; i < transactions.size(); i++) {
        String[] fields = transactions.get(i).split(",");
        if (fields[0].equals(transactionIdToUpdate) && fields[1].equalsIgnoreCase("Accepted") && fields[8].equals(this.getUserID())) {
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

        System.out.println("Task History (Completed or Cancelled):");
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] fields = line.split(",");

            // Check if the transaction status is either Completed or Cancelled
            if (fields[1].equalsIgnoreCase("Completed") || fields[1].equalsIgnoreCase("Cancelled")) {
                System.out.println(line); // Print the transaction
            }
        }

        // Add a prompt to go back to the menu
        System.out.println("\nPress 1 to go back to the menu.");
        int input = scanner.nextInt();
        while (input != 1) {
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
        // Implementation
    }

    private void revenueDashboard() {
        // Implementation
    }

    
    @Override
    public void Financial_Dashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
