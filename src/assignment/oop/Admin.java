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
public class Admin extends User{
    private final Scanner scanner;
    
    public Admin(String username, String password) {
        super(username, password);
        this.scanner = new Scanner(System.in); 
    }

    @Override
    public void displayMenu() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    } 
    
}
