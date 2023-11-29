/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

import java.util.Scanner;

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
            System.out.println("2. Accept/Decline Task");
            System.out.println("3. Update Task Status");
            System.out.println("4. Check Task History");
            System.out.println("5. Read Customer Review");
            System.out.println("6. Revenue Dashboard");
            System.out.println("0. Exit");
            
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (choice) {
                case 1:
                    viewTask();
                    break;
                case 2:
                    acceptDeclineTask();
                    break;
                case 3:
                    updateTaskStatus();
                    break;
                case 4:
                    checkTaskHistory();
                    break;
                case 5:
                    readCustomerReview();
                    break;
                case 6:
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
    }

    private void acceptDeclineTask() {
        // Implementation
    }

    private void updateTaskStatus() {
        // Implementation
    }

    private void checkTaskHistory() {
        // Implementation
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
