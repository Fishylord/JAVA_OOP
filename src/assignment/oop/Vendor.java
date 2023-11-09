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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public abstract class Vendor extends User implements UserFunctionalities {
    private List<Item> items; // A list to hold the vendor's items might be removed if unused
    
    public Vendor(String username, String password) {
        super(username, password);
        this.items = new ArrayList<>(); // Initialize the items list Might be removed if unused
    }
    
    public boolean addItem(Item item) {
        if (!duplicationCheck(item.getName())) {
            item.add(item);
            try (FileWriter fw = new FileWriter("Food.txt", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(item.toString());
            } catch (IOException e) {
                // Handle the exception
                System.out.println("An error occurred while writing to Food.txt.");
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            System.out.println("An item with the same name already exists for this vendor.");
            return false;
        }
    }
    
    public void removeItem(Item item){
        
    }
    

    @Override
    public void showFunctionalities() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
    
    
    
    
    public boolean duplicationCheck(String itemName) {
        User currentUser = Session.getCurrentUser();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("Food.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(","); //Check Accound ID if Name already exist only if it doesn't it returns fine to allow for adding
                if (itemData[1].trim().equals(this.getUsername()) && itemData[2].trim().equalsIgnoreCase(itemName)) {
                    return true; //A Previous Name isfound
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from Food.txt.");
            e.printStackTrace();
        }
        return false;
    }
}
