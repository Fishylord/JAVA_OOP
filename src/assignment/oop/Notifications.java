/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

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

    // toString
    @Override
    public String toString() {
        return "Transaction{" + "accountId='" + accountId + '\'' + ", notificationId='" + notificationId + '\'' + ", notificationMsg='" + notificationMsg + '\'' + ", status='" + status + '\'' +'}';
    }
}

