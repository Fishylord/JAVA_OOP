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
    private double pricing;
    private String date;
    private String vendorAccountId;
    private String customerAccountId;
    private String runnerId;

    // Constructor
    public Transactions(String transactionId, String status, String foodId, double pricing, String date, String vendorAccountId, String customerAccountId, String runnerId) {
        this.transactionId = transactionId;
        this.status = status;
        this.foodId = foodId;
        this.pricing = pricing;
        this.date = date;
        this.vendorAccountId = vendorAccountId;
        this.customerAccountId = customerAccountId;
        this.runnerId = runnerId;
    }


    public String getTransactionId() {return transactionId;}
    public String getStatus() {return status;}
    public String getFoodId() {return foodId;}
    public double getPricing() {return pricing;}
    public String getDate() {return date;}
    public String getVendorAccountId() {return vendorAccountId;}
    public String getCustomerAccountId() {return customerAccountId;}
    public String getRunnerId() {return runnerId;}

    public void setTransactionId(String transactionId) {this.transactionId = transactionId;}
    public void setStatus(String status) {this.status = status;}
    public void setFoodId(String foodId) {this.foodId = foodId;}
    public void setPricing(double pricing) {this.pricing = pricing;}
    public void setDate(String date) {this.date = date;}
    public void setVendorAccountId(String vendorAccountId) {this.vendorAccountId = vendorAccountId;}
    public void setCustomerAccountId(String customerAccountId) {this.customerAccountId = customerAccountId;}
    public void setRunnerId(String runnerId) {this.runnerId = runnerId;}

    // toString
    @Override
    public String toString() {return "Transaction{" + "transactionId='" + transactionId + '\'' + ", status='" + status + '\'' + ", foodId='" + foodId + '\'' + ", pricing=" + pricing + ", date='" + date + '\'' + ", vendorAccountId='" + vendorAccountId + '\'' + ", customerAccountId='" + customerAccountId + '\'' + ", runnerId='" + runnerId + '\'' + '}';
    }
}
    
