/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

/**
 *
 * @author User
 */
public class Transactions {
    private String transactionId;
    private String status;
    private String foodId;
    private int quantity;
    private double total_price;
    private String date;
    private String vendorAccountId;
    private String customerAccountId;
    private String runnerId;

    // Constructor
    public Transactions(String transactionId, String status, String foodId, int quantity, double totalPrice, String date, String vendorAccountId, String customerAccountId) {
        this.transactionId = transactionId;
        this.status = status;
        this.foodId = foodId;
        this.quantity = quantity;
        this.total_price = totalPrice;
        this.date = date;
        this.vendorAccountId = vendorAccountId;
        this.customerAccountId = customerAccountId;
        this.runnerId = runnerId;
    }

    public String getTransactionId() {return transactionId;}
    public String getStatus() {return status;}
    public String getFoodId() {return foodId;}
    public int getquantity() {return quantity;}
    public double getTotalPricing() {return total_price;}
    public String getDate() {return date;}
    public String getVendorAccountId() {return vendorAccountId;}
    public String getCustomerAccountId() {return customerAccountId;}
    public String getRunnerId() {return runnerId;}

    public void setTransactionId(String transactionId) {this.transactionId = transactionId;}
    public void setStatus(String status) {this.status = status;}
    public void setFoodId(String foodId) {this.foodId = foodId;}
    public void setQuantity(int quantity) {this.quantity= quantity;}
    public void setTotalPricing(double total_price) {this.total_price = total_price;}
    public void setDate(String date) {this.date = date;}
    public void setVendorAccountId(String vendorAccountId) {this.vendorAccountId = vendorAccountId;}
    public void setCustomerAccountId(String customerAccountId) {this.customerAccountId = customerAccountId;}
    public void setRunnerId(String runnerId) {this.runnerId = runnerId;}

    // toString
    @Override
    public String toString() {
        String currentUserType = Login.getUserType();
        switch (currentUserType) {
            case "Vendor":
                return "TransactionID= " + transactionId + ", status= " + status + ", foodId= " + foodId + ", Quantity= " + quantity +", Total Price= " + total_price + ", date= " + date + ", vendorAccountId= '" + vendorAccountId + ", customerAccountId= " + customerAccountId + ", runnerId= " + runnerId;
            case "Delivery":
                return "TransactionID= " + transactionId + ", status= " + status + ", foodId= " + foodId + ", Quantity= " + quantity +", Total Price= " + total_price + ", date= " + date + ", vendorAccountId= '" + vendorAccountId + ", customerAccountId= " + customerAccountId + ", runnerId= " + runnerId;
            case "Customer":
                return "TransactionID= " + transactionId + ", status= " + status + ", foodId= " + foodId + ", Quantity= " + quantity +", Total Price= " + total_price + ", date= " + date;
            default:
                return toString(); //May not be used.

        }
    }
    
}
    
