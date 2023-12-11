/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.oop;

/**
 *
 * @author ck
 */

public class Food {
    private String id;
    private String name;
    private String description;
    private double rating;

    public Food(String id, String name, String description, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
    }
    
       public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("%s: %s - %s (Rating: %.1f)", id, name, description, rating);
    }
}

