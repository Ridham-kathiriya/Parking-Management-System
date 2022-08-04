package Modules.Review.controller;

import java.sql.SQLException;

import static Utils.Constants.stmt;

public class DeleteReviewAndRating {
    public void deleteReviewAndRating(int parkingID)
    {
        String deleteQuery = "DELETE FROM reviews_and_ratings WHERE PARKING_ID = "+parkingID;
        try {
            stmt.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
