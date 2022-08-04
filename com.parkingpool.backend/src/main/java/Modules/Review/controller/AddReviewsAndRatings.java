package Modules.Review.controller;

import Modules.Review.model.Review;
import Utils.Constants;

import java.sql.SQLException;
import java.sql.Statement;

public class AddReviewsAndRatings {

        Statement stmt = Constants.stmt;
        public boolean addReviewAndRating(Review review) {
            int parkingId = review.getParkingID();
            int userID = review.getUserID();
            String reviews = review.getReviews();
            double ratings = review.getRatings();
            String addReviewAndRatingQuery = "INSERT INTO reviews_and_ratings (parking_id, user_id, reviews, ratings) VALUES(" + parkingId + " , '"+userID+"' , '" + reviews + "' , '" + ratings + "');";

            try {
                boolean isAdded = stmt.execute(addReviewAndRatingQuery);
                if(isAdded){
                    System.out.println("Review and Rating added successfully");
                    return true;
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Review and Rating not added");
                return false;
            }
        }
}