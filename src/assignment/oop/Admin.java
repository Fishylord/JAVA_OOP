/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author User
 */
public class Admin extends User{
    private final Scanner scanner;
    private int nextVendorID = 1;
    private int nextCustomerID = 1;
    private int nextDeliveryID = 1;
    private int receiptCounter = 1;

    
    
    public Admin(String username, String password, String userID) {
        super(username, password, userID);
        this.scanner = new Scanner(System.in);
        initializeNextID("Vendor");
        initializeNextID("Customer");
        initializeNextID("Delivery");
        initializeNextID("Receipt");
    }

    @Override
    public void displayMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("=========Admin Menu=========");
            System.out.println("1. Create New Account");
            System.out.println("2. Edit Account");
            System.out.println("3. Delete Account");
            System.out.println("4. Check Lists of Accounts");
            System.out.println("5. Top Up Credits");
            try {
                if (hasUnreadNotifications()) {
                    System.out.println("6. Notifications (!)");
                } else {
                    System.out.println("6. Notifications");
                }
            } 
            catch (IOException e) {
                System.out.println("Error checking notifications.");
            } //Additional Feature.
            System.out.println("0. Exit");
            
            System.out.println("Enter Your Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    int createChoice = -1;
                    while (createChoice != 0){
                        System.out.println("====Account Creation====");
                        System.out.println("1. Vendor Account");
                        System.out.println("2. Customer Account");
                        System.out.println("3. Food Runner Account");
                        System.out.println("0. Exit");

                        System.out.print("Enter Your Choice: ");
                        createChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (createChoice) {
                            case 1:
                                createAccount("Vendor");
                                break;
                            case 2:
                                createAccount("Customer");
                                break;
                            case 3:
                                createAccount("Delivery");
                                break;
                            case 0:
                                System.out.println("Exiting menu...");
                                logout();
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }                        
                    }
                    break;
                    
                case 2:
                    int EditChoice = -1;
                    while (EditChoice != 0) {
                        System.out.println("====Account Editing====");
                        System.out.println("1. Vendor Account");
                        System.out.println("2. Customer Account");
                        System.out.println("3. Food Runner Account");
                        System.out.println("0. Exit");

                        System.out.print("Enter Your Choice: ");
                        EditChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (EditChoice) {
                            case 1:
                                editAccount("Vendor");
                                break;
                            case 2:
                                editAccount("Customer");
                                break;
                            case 3:
                                editAccount("Delivery");
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
                    
                case 3:
                    int deleteChoice = -1;
                    while (deleteChoice != 0) {
                        System.out.println("====Account Deletion====");
                        System.out.println("1. Vendor Account");
                        System.out.println("2. Customer Account");
                        System.out.println("3. Food Runner Account");
                        System.out.println("0. Exit");

                        System.out.print("Enter Your Choice: ");
                        deleteChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (deleteChoice) {
                            case 1:
                                deleteAccount("Vendor");
                                break;
                            case 2:
                                deleteAccount("Customer");
                                break;
                            case 3:
                                deleteAccount("Delivery");
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
                
                case 4:
                    int readChoice = -1;
                    while (readChoice != 0) {
                        System.out.println("====Lists of Accounts====");
                        System.out.println("1. Read Vendor Accounts");
                        System.out.println("2. Read Customer Accounts");
                        System.out.println("3. Read Food Runner Accounts");
                        System.out.println("0. Exit");

                        System.out.print("Enter Your Choice: ");
                        readChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (readChoice) {
                            case 1:
                                readAccount("Vendor");
                                break;
                            case 2:
                                readAccount("Customer");
                                break;
                            case 3:
                                readAccount("Delivery");
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
                case 5:
                    int topUpChoice = -1;
                    while (topUpChoice != 0) {
                        System.out.println("====Topping Up Accounts====");
                        System.out.println("1. Top Up Vendor Accounts");
                        System.out.println("2. Top Up Customer Accounts");
                        System.out.println("3. Top Up Food Runner Accounts");
                        System.out.println("0. Exit");

                        System.out.print("Enter Your Choice: ");
                        topUpChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (topUpChoice) {
                            case 1:
                                topUpAccount("Vendor");
                                break;
                            case 2:
                                topUpAccount("Customer");
                                break;
                            case 3:
                                topUpAccount("Delivery");
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
                case 6:
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
            // ---------------------------------------Not done ----------------------------------------------
            }
        } 
    
    private void saveAccountToFile(String username, String password, String accountType, double balance, String formattedID) {
        try (FileWriter writer = new FileWriter("Accounts.txt", true)) {
            // Append account information to the file
            writer.write(username + "," + password + "," + accountType + "," + balance + "," + formattedID + "\n");
            System.out.println("Account information saved to file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    
    public void createAccount(String accountType){
        System.out.println("Creating " + accountType + " Account" );
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        switch (accountType) {
            case "Vendor":
                saveAccountToFile(username, password, accountType, 0.0, "VEN" + String.format("%03d", nextVendorID++));
                break;

            case "Customer":
                saveAccountToFile(username, password, accountType, 0.0, "CUS" + String.format("%03d", nextCustomerID++));
                break;

            case "Delivery":
                saveAccountToFile(username, password, accountType, 0.0, "RUN" + String.format("%03d", nextDeliveryID++));
                break;

            default:
                System.out.println("Invalid account type.");
                break;
        }
    }  
    
    public void editAccount(String accountType) {
        System.out.println("Editing " + accountType + " Account");

        System.out.print("Enter account ID to edit: ");
        String accountID = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            StringBuilder fileContent = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                String[] accountInfo = line.split(",");
                String existingID = accountInfo[4]; // Assuming ID is at index 4, adjust if needed

                if (existingID.equals(accountID)) {
                    // Display existing account information
                    System.out.println("Current Account Information:");
                    System.out.println("Username: " + accountInfo[0]);
                    System.out.println("Password: " + accountInfo[1]);
                    System.out.println("Balance: " + accountInfo[3]); // Assuming balance is at index 3

                    // Allow user to edit fields
                    System.out.print("Enter new username (press Enter to keep current): ");
                    String newUsername = scanner.nextLine();
                    if (!newUsername.isEmpty()) {
                        accountInfo[0] = newUsername;
                    }

                    System.out.print("Enter new password (press Enter to keep current): ");
                    String newPassword = scanner.nextLine();
                    if (!newPassword.isEmpty()) {
                        accountInfo[1] = newPassword;
                    }

                    // Save the updated information
                    line = String.join(",", accountInfo);
                    System.out.println("Account information updated.");
                }

                fileContent.append(line).append("\n");
            }

            // Write the updated content back to the file
            try (FileWriter writer = new FileWriter("Accounts.txt")) {
                writer.write(fileContent.toString());
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    
    public void deleteAccount(String accountType) {
        System.out.println("Deleting " + accountType + " Account");

        System.out.print("Enter account ID to delete: ");
        String accountID = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            StringBuilder fileContent = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                String[] accountInfo = line.split(",");
                String existingID = accountInfo[4]; // Assuming ID is at index 4, adjust if needed

                if (!existingID.equals(accountID)) {
                    // Include the account information in the file content if it is not the one to be deleted
                    fileContent.append(line).append("\n");
                } else {
                    System.out.println("Account with ID " + accountID + " deleted.");
                }
            }

            // Write the updated content back to the file
            try (FileWriter writer = new FileWriter( "Accounts.txt")) {
                writer.write(fileContent.toString());
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public void readAccount(String accountType) {
        System.out.println("Reading " + accountType + " Accounts");

        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] accountInfo = line.split(",");
                if (accountInfo[2].equals(accountType)) {                
                    String username = accountInfo[0];
                    String password = accountInfo[1];
                    double balance = Double.parseDouble(accountInfo[3]);
                    String accountID = accountInfo[4];

                    System.out.println("Account ID: " + accountID);
                    System.out.println("Username: " + username);
                    System.out.println("Password: " + password);
                    System.out.println("Balance: " + balance);
                    System.out.println("=====================");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    
    private void initializeNextID(String Counters) {
        int nextID = 1;
        String filePath;

        switch (Counters) {
            case "Vendor":
                filePath = "Accounts.txt";
                break;
            case "Customer":
                filePath = "Accounts.txt";
                break;
            case "Delivery":
                filePath = "Accounts.txt";
                break;
            case "Receipt":
                filePath = "AllReceipts.txt";
                break;
            default:
                System.out.println("Invalid Choice.");
                return; // Return without further processing for invalid counters
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            if (filePath.equals("Accounts.txt")) {
                while ((line = reader.readLine()) != null) {
                    String[] accountInfo = line.split(",");
                    if (Counters.equals(accountInfo[2])) {
                        int existingID = Integer.parseInt(accountInfo[4].substring(3));
                        nextID = Math.max(nextID, existingID + 1);
                    }
                }
            } else if (filePath.equals("AllReceipts.txt")) {
                while ((line = reader.readLine()) != null) {
                    String[] accountInfo = line.split(",");
                    int existingID = Integer.parseInt(accountInfo[0].substring(3));
                    nextID = Math.max(nextID, existingID + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    

        switch (Counters) {
            case "Vendor":
                nextVendorID = nextID;
                break;
            case "Customer":
                nextCustomerID = nextID;
                break;
            case "Delivery":
                nextDeliveryID = nextID;
                break;
            case "Receipt":
                receiptCounter = nextID;
                break;
            default:
                System.out.println("Invalid account type.");
                break;
        }
    }

    
    public void topUpAccount(String accountType) {
        System.out.println("Top Up " + accountType + " Account");

        System.out.print("Enter account ID to top up: ");
        String accountID = scanner.nextLine();

        System.out.print("Enter the amount to top up: ");
        double amount = 0.0;
        try {
            amount = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character left by nextDouble()
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for amount. Please enter a valid number.");
            scanner.nextLine(); // Clear the invalid input
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"));
            FileWriter notificationsWriter = new FileWriter("Notifications.txt", true)) {
            String line;
            StringBuilder fileContent = new StringBuilder();
            boolean accountFound = false;

            while ((line = reader.readLine()) != null) {
                String[] accountInfo = line.split(",");
                String existingID = accountInfo[4]; // Assuming ID is at index 4, adjust if needed

                if (existingID.equals(accountID)) {
                    accountFound = true;
                    // Display existing account information
                    System.out.println("Current Account Information:");
                    System.out.println("Username: " + accountInfo[0]);
                    System.out.println("Password: " + accountInfo[1]);
                    System.out.println("Balance: " + accountInfo[3]);

                    try {
                        // Top up the account
                        double currentBalance = Double.parseDouble(accountInfo[3]);
                        double newBalance = currentBalance + amount;
                        accountInfo[3] = String.valueOf(newBalance);
                        System.out.println("Account topped up by " + amount + ". New balance: " + newBalance);

                        // Generate and save receipt
                        generateReceipt(accountType, accountID, amount, currentBalance);
                        
                        // Append notification to Notifications.txt
                        String notification = "Your account topped up by " + amount + ". New balance: " + newBalance;
                        int notificationID = getNotificationIDCounter();
                        String notificationStatus = "Unread.";
                        notificationsWriter.write(accountInfo[4]+ "," + "NOT"+notificationID + "," + notification + "," + notificationStatus + "\n");
                        System.out.println(notification + " (Notification also added to Notifications.txt)");
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Error updating balance. Please try again.");
                        return;
                    }
                }

                // Append account information to the file
                fileContent.append(String.join(",", accountInfo)).append("\n");
            }

            if (!accountFound) {
                System.out.println("Account with ID " + accountID + " not found.");
                return;
            }

            // Write the updated content back to the file
            try (FileWriter writer = new FileWriter("Accounts.txt")) {
                writer.write(fileContent.toString());
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    
    public void generateReceipt(String accountType, String accountID, double amount, double oldBalance) {
        String receiptFileName = "AllReceipts.txt";

        try (FileWriter writer = new FileWriter(receiptFileName, true)) {
            // Generate receipt ID with the counter
            String receiptID = "REC" + String.format("%03d", receiptCounter++);

            String currentTime = getDate();
            double newBalance = oldBalance + amount;

            // Write receipt information in the desired format
            writer.write(receiptID + "," + currentTime + "," + accountID + "," +
                         oldBalance + "," + amount + "," + newBalance + "\n");

            System.out.println("Receipt generated successfully. Appended to: " + receiptFileName);
        } catch (IOException e) {
            System.err.println("Error writing receipt file: " + e.getMessage());
        }
    }
}
