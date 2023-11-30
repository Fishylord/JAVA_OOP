/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author User
 */
public class Notifications {
    private String accountId;
    private String notificationId;
    private String notificationMsg;
    private String status;

    // Constructor
    public Notifications(String accountId, String notificationId, String notificationMsg, String status) {
        this.accountId = accountId;
        this.notificationId = notificationId;
        this.notificationMsg = notificationMsg;
        this.status = status;
        writeToFile();
    }

    // Getters
    public String getAccountId() {
        return accountId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    private void writeToFile() {
        try (FileWriter writer = new FileWriter("Notifications.txt", true)) {
            // Format the notification for writing to the file
            String line = accountId + "," + notificationId + "," + notificationMsg + "," + status;
            writer.write(line + "\n");
            System.out.println("Notification saved to file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    // toString
    @Override
    public String toString() {
        return "Transaction{" + "accountId='" + accountId + '\'' + ", notificationId='" + notificationId + '\'' + ", notificationMsg='" + notificationMsg + '\'' + ", status='" + status + '\'' +'}';
    }
}

