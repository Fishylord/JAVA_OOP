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
    private double rating;
    private String reviewMsg;
    private int runnerrating;
    private String runnerreviewMsg;
    private String userId;
    private String runnerId;
    private Login.UserType userType;
    
    public Reviews(String foodId, double rating, String reviewMsg, int runnerrating, String runnerreviewMsg, String userId, String runnerId) {
        this.foodId = foodId;
        this.rating = rating;
        this.reviewMsg = reviewMsg;
        this.runnerrating = runnerrating;
        this.runnerreviewMsg = runnerreviewMsg;
        this.userId = userId;
        this.runnerId = runnerId;
        
    }

   
    public String getFoodId() {return foodId;}
    public void setFoodId(String foodId) {this.foodId = foodId;}
    public double getRating() {return rating;}
    public void setRating(double rating) {this.rating = rating;}
    public String getReviewMsg() {return reviewMsg;}
    public void setReviewMsg(String reviewMsg) {this.reviewMsg = reviewMsg;}
    public String getUserID() {return userId;}      
    public void setUserId(String userId){this.userId = userId;}
    public String getRunnerId() {return runnerId;}
    
    public int getRunnerRating() {return runnerrating;}
    public void setRunnerRating(int runnerrating) {this.runnerrating = runnerrating;}
    public String getRunnerReviewMsg() {return runnerreviewMsg;}
    public void setRunnerReviewMsg(String runnerreviewMsg) {this.runnerreviewMsg = runnerreviewMsg;}
    
    public void setRunnerId(String runnerId) {this.runnerId = runnerId;}
    
    //Remember delivery driver will not be seeing the rating and reviewMsg for the food. just foodid rating reviewMsg.

     @Override
    public String toString() {
        String currentUserType = Login.getUserType();
        switch (currentUserType) {
            case "Vendor":
                return "Food ID: " + foodId + ", Customer Rating: " + rating + ", Review: " + reviewMsg;
            case "Delivery":
                return "Food ID: " + foodId + ", Runner Rating: " + runnerrating + ", Runner Review: " + runnerreviewMsg;
            case "Customer":
                return "Food ID: " + foodId + ", Customer Rating: " + rating + ", Review: " + reviewMsg;
            default:
                return toString(); //May not be used.

        }
    }
    public String toFileString() {
        // For saving to a file only.
        return foodId + "," + rating + "," + reviewMsg + "," + runnerrating + "," + runnerreviewMsg + "," + userId + "," + runnerId;
    }
}
