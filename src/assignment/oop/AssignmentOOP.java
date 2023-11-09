/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment.oop;

import java.util.Scanner;

/**
 *
 * @author User
 */
public class AssignmentOOP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Example user details, hardcoded for demonstration purposes
        String username = "exampleVendor";
        String password = "password123";

        // Create a Vendor object with the hardcoded details
        Vendor currentVendor = new Vendor(username, password);

        // Scanner for console input
        Scanner scanner = new Scanner(System.in);

        // Perform actions for the vendor
        Actions.vendorActions(currentVendor);

        // Close the scanner
        scanner.close();
    }
    
}
