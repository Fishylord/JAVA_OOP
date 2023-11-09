/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

/**
 *
 * // When a user logs in successfully:
Session.login(user);

// To get the current user:
User currentUser = Session.getCurrentUser();

// When a user logs out:
Session.logout();
* 
* ^To Be added in the Future
 */
public class Session {
    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    static User getCurrentUser() {
        return currentUser;
    }

    // Function for checking if someone is logged in (May be removed in future)
    public static boolean isLoggedIn() {
        return currentUser != null;
    }


    
    
}
