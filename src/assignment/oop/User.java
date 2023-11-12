/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

/**
 *
 * @author User
 */
public abstract class User implements UserFunctionalities {
    private String username;
    private String password;
    private String userid;
    public final static int PAGE_SIZE = 6;
    
    public User(String username, String password, String userID) {
        this.username = username;
        this.password = password;
        this.userid  = userID;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void SetUsername(String username){
        this.username = username;
    }
    
    public String getUserID() {
        return userid;
    }
    
    public void SetUserID(String username){
        this.userid = userid;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password= password;
    }
}
