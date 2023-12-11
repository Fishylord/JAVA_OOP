/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;
import java.io.*;
import java.util.*;
/**
 *
 * @author User
 */
public class Login {
    private Scanner scanner;
    private static String userType;

    public Login() {
        scanner = new Scanner(System.in);
    }

    public User authenticate(String username, String password) {
        try {
            File file = new File("Accounts.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String[] accountDetails = fileScanner.nextLine().split(",");
                // Assuming each line in Accounts.txt is in the format: username,password,accountType
                if (accountDetails[0].equals(username) && accountDetails[1].equals(password)) {
                    userType = accountDetails[2].trim();
                    String userID = accountDetails[4];
                    switch (accountDetails[2].toLowerCase()) {
                        case "vendor":
                            return new Vendor(username, password, userID);
                        case "customer":
                            return new Customer(username,password, userID);
                        case "delivery":
                            return new Delivery(username,password, userID);
                        case "admin":
                            return new Admin(username,password, userID);
                            
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while accessing the accounts file.");
            e.printStackTrace();
        }
        return null; // Return null if authentication fails
    }

    public void promptLogin() {
        while (true) {
            System.out.println("Enter Username (or type 'exit' to quit): ");
            String username = scanner.nextLine();

            // Check if the user wants to exit
            if ("exit".equalsIgnoreCase(username)) {
                break;
            }

            System.out.println("Enter Password: ");
            String password = scanner.nextLine();

            User user = authenticate(username, password);

            if (user != null) {
                System.out.println("Login successful!");
                user.displayMenu(); 
                break; 
            } else {
                System.out.println("Login failed. Please check your credentials.");
            }
        }
    }

    public enum UserType {
        Vendor,
        Customer,
        Delivery,
        Admin
    }
    
    public static String getUserType(){
        return userType;
    }
}

