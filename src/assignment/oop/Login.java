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
    private String accountType;

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
                    // Return a new instance of the user type (e.g., Vendor, Admin, etc.)
                    switch (accountDetails[2].toLowerCase()) {
                        case "vendor":
                            return new Vendor(username, password);
                        case "customer":
                            return new Customer(username,password);
                        case "delivery":
                            return new Delivery(username,password);
                        case "admin":
                            return new Admin(username,password);
                            
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
    System.out.println("Enter Username: ");
    String username = scanner.nextLine();
    System.out.println("Enter Password: ");
    String password = scanner.nextLine();

    User user = authenticate(username, password);

    if (user != null) {
        System.out.println("Login successful!");
        user.displayMenu(); // Directly call displayMenu on the User object
    } else {
        System.out.println("Login failed. Please check your credentials.");
    }
}


    
}

