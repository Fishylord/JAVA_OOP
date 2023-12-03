/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
            System.out.println("1. Read customer review");
            System.out.println("2. Place/Cancel order");
            System.out.println("3. Check order status");
            System.out.println("4. Check order history");
            System.out.println("5. Check transaction history");
            System.out.println("6. Provide a review for each order");
            System.out.println("7. Reorder using order history");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt(); // Clear the scanner buffer
            scanner.nextLine(); // Consume the newline left by nextInt()
            switch (choice) {
                case 1:
                    // readCustomerReview();
                    break;
                case 2:
                    // placeOrCancelOrder();
                    break;
                case 3:
                    // checkOrderStatus();
                    break;
                case 4:
                    // checkOrderHistory();
                    break;
                case 5:
                    // checkTransactionHistory();
                    break;
                case 6:
                    // provideReviewForOrder();
                    break;
                case 7:
                    // reorderUsingOrderHistory();
                    break;
                case 0:
                    System.out.println("Exiting menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    @Override
    public void Financial_Dashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
