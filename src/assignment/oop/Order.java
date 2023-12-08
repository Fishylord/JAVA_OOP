/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author User
 */
public class Order {
    private String transactionid;
    private String status; 
    private String foodid;
    private int quantity;
    private double total_price;
    private String date;
    private String vendorid;
    private String customerid;
    private String runnerId;

    public Order(String transactionId, String status, String foodId, int quantity, double totalPrice, String date, String vendorId, String customerId, String runnerId) {
        this.transactionid = transactionId;
        this.status = status;
        this.foodid = foodId;
        this.quantity = quantity;
        this.total_price = totalPrice;
        this.date = date;
        this.vendorid = vendorId;
        this.customerid = customerId;
        this.runnerId = runnerId;
    }
    
    public String getTransactionId() { return transactionid; }
    public String getStatus() { return status; }
    public String getFoodId() { return foodid; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return total_price; }
    public String getDate() { return date; }
    public String getVendorId() { return vendorid; }
    public String getCustomerId() { return customerid; }
    public String getRunnerId() {return runnerId;}
    
    public void setTransactionId(String transactionId) { this.transactionid = transactionId; }
    public void setStatus(String status) { this.status = status; }
    public void setFoodId(String foodId) { this.foodid = foodId; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setTotalPrice(double totalPrice) { this.total_price = totalPrice; }
    public void setDate(String date) { this.date = date; }
    public void setVendorId(String vendorId) { this.vendorid = vendorId; }
    public void setCustomerId(String customerId) { this.customerid = customerId; }
    public void setRunnerId(String runnerId) {this.runnerId = runnerId;}
    
    public static void saveOrders(List<Order> orders, String filePath) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            for (Order order : orders) {
                out.println(order.toString());
            }
        } catch (IOException e) {
            throw new IOException("An error occurred while saving orders to the file.", e);
        }
    }
    
    @Override
    public String toString() {
        // Format the output as needed for writing back to the file
        return String.join(",", transactionid, status, foodid, String.valueOf(quantity), String.valueOf(total_price), date, vendorid, customerid);
    }
    public String toFileString() {
        // Format the output as needed for writing back to the file
        return String.join(",", transactionid, status, foodid, String.valueOf(quantity), String.valueOf(total_price), date, vendorid, customerid, runnerId);
    }
}
