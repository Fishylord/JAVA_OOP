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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */
public abstract class User implements UserFunctionalities {
    private String username;
    private String password;
    private String userid;
    public final static int PAGE_SIZE = 6;
    private final Scanner scanner;
    abstract void Financial_Dashboard();
    
    public User(String username, String password, String userID) {
        this.username = username;
        this.password = password;
        this.userid  = userID;
        this.scanner = new Scanner(System.in); //Don't touch
    }
    
    public String getUsername() {
        return username;
    }
    
    public void SetUsername(String username){
        this.username = username;
    }
    
    public String getUserID() {
        return userid;
    }
    
    public void SetUserID(String username){
        this.userid = userid;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password= password;
    }
    
    
    
    
    
    public final void readNotifications() throws IOException {
    // Load notifications for the logged-in user
    List<String> notifications = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("Notifications.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] notificationData = line.split(",");
            // Assuming format: UserID, NotificationID, NotificationMsg, Unread/Read
            if (notificationData[0].trim().equals(this.getUserID())) {
                String notificationStatus = notificationData[3].trim();
                notifications.add(notificationData[1].trim() + ": " + notificationData[2].trim() + " [" + notificationStatus + "]");
            }
        }
    }

    if (notifications.isEmpty()) {
        System.out.println("No notifications found for this user.");
        return;
    }
    boolean hasUnread = hasUnreadNotifications();//this is just for action to appear btw.
    
    // Paginate and display notifications
    int currentPage = 0;
    while (true) {
        int start = currentPage * PAGE_SIZE;
        int end = Math.min((currentPage + 1) * PAGE_SIZE, notifications.size());

        System.out.println("Notifications (Page " + (currentPage + 1) + "):");
        for (int i = start; i < end; i++) {
            System.out.println(notifications.get(i));
        }
        //System.out.println(hasUnread);

        if (currentPage > 0) {
            System.out.println("1. Previous Page");
        }
        if (end < notifications.size()) {
            System.out.println("2. Next Page");
        }
        if (hasUnread) {
            System.out.println("3. Clear Notifications");
        } //In a case in which Action 1,2 doesn't appear due to less than 6 items for a page to appear, it will still remain 3 simply because a dynamic numbering and accepting that dynamic numbering is to advance
        System.out.println("0. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1 && currentPage > 0) {
            currentPage--;
        } else if (choice == 2 && end < notifications.size()) {
            currentPage++;
        } else if (choice == 3 && hasUnread) {
            clearNotifications();
            // Update the hasUnread flag and notifications list
            hasUnread = false;
            notifications.replaceAll(n -> n.endsWith("[Unread.]") ? n.replace("[Unread.]", "[Read]") : n);
        } else if (choice == 0) {
            break;
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
}

    // Method to clear notifications
    public final void clearNotifications() throws IOException {
        List<String> updatedNotifications = new ArrayList<>();
        boolean changesMade = false;

        // Read the notifications and update their status
        try (BufferedReader br = new BufferedReader(new FileReader("Notifications.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] notificationData = line.split(",");
                if (notificationData[0].trim().equals(this.getUserID()) && "Unread.".equalsIgnoreCase(notificationData[3].trim())) {
                    // Change status to "Read"
                    notificationData[3] = "Read";
                    changesMade = true;
                }
                // Reconstruct the line and add to the updated list
                updatedNotifications.add(String.join(",", notificationData));
            }
        }

        // Write the updated notifications back to the file, if changes were made
        if (changesMade) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("Notifications.txt"))) {
                for (String updatedLine : updatedNotifications) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            }
            System.out.println("All notifications have been marked as read.");
        } else {
            System.out.println("No new notifications to clear.");
        }
    }
    

    // Check for unread notifications
    public final boolean hasUnreadNotifications() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("Notifications.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] notificationData = line.split(",");
                if (notificationData[0].trim().equals(this.getUserID()) && notificationData[3].trim().equalsIgnoreCase("Unread.")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    public static String getDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return today.format(formatter);
    }
    
    public static int getNotificationIDCounter() {
        int nextID = 1;
        String filePath = "Notifications.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] notificationInfo = line.split(",");
                int existingID = extractNumericPart(notificationInfo[1]);
                nextID = Math.max(nextID, existingID + 1);
            }
        } catch (IOException e) {
        }

        return nextID;
    }
    public static int getTransactionIDCounter() {
        int nextID = 1;
        String filePath = "Transactions.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] notificationInfo = line.split(",");
                int existingID = extractNumericPart(notificationInfo[0]);
                nextID = Math.max(nextID, existingID + 1);
            }
        } catch (IOException e) {
        }

        return nextID;
    }
    
    public static int getNextItemID() {
        int nextID = 1;
        String filePath = "Food.txt"; 

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemInfo = line.split(",");
                int existingID = extractNumericPart(itemInfo[0]);
                nextID = Math.max(nextID, existingID + 1);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading Food.txt.");
            e.printStackTrace();
        }

        return nextID;
    }
    
    private static int extractNumericPart(String input) {
        String numericPart = input.replaceAll("[^0-9]", "");
        return numericPart.isEmpty() ? 0 : Integer.parseInt(numericPart);
    }

    protected final void logout() {
        System.out.println("Logging out...");
        
        // clear data in-case
        this.username = null;
        this.password = null;
        this.userid = null;

        
        System.out.println("You have been successfully logged out.");

        Login login = new Login();
        login.promptLogin();
    }
    
}
