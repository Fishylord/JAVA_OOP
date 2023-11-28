/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

/**
 *
 * @author User
 */
public class Reviews {
   private String foodId;
    private int rating;
    private String reviewMsg;
    private String userId;

    public Reviews(String foodId, int rating, String reviewMsg, String userId) {
        this.foodId = foodId;
        this.rating = rating;
        this.reviewMsg = reviewMsg;
        this.userId = userId;
    }

    // Getters and Setters
    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public int getRating() {
        return rating;
    }
    

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewMsg() {
        return reviewMsg;
    }

    public void setReviewMsg(String reviewMsg) {
        this.reviewMsg = reviewMsg;
    }
    
    public String getUserID() {
        return userId;
    }
            
    public void setUserId(String userId){
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "Food ID: " + foodId + ", Rating: " + rating + ", Review: " + reviewMsg + ", User ID: " + userId;
    }
}
