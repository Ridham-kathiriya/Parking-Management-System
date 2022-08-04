package Modules.Review.controller;

import Modules.Review.model.Review;
import Utils.Constants;

import java.sql.SQLException;

public class UpdateReviewAndRating {
    public void updateReviewAndRating(Review review) {
        int reviewId = review.getReview_id();
        double ratings = review.getRatings();
        String reviewText = review.getReviews();

        String query = "UPDATE reviews_and_ratings SET ratings=" + ratings + ", reviews=\"" + reviewText + "\" WHERE review_id = " + reviewId;
        try {
            Constants.stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updating review and rating");
        }
    }
}
