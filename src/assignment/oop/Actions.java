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
public class Actions {
    public static void vendorActions(Vendor vendor){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. XXX");
            //If Function for IF notifications > 0 (unread)(Unseen) Display "Notiftication(!)" if not (Notifications).
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // vendor.addItem()
                    // Function that Checks Input and Erros etc.You 
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    public static void customerActions(Customer customer){
        
    }
    
    public static void deliveryActions(Delivery delivery){
        
    }
    
    public static void adminActions(Admin admin){
        
    }
}
