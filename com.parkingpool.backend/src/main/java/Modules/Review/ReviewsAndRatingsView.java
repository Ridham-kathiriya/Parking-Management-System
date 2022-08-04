package Modules.Review;

import Modules.Review.controller.AddReviewsAndRatings;
import Modules.Review.model.Review;
import Modules.User.Utils.UserUtils;
import Utils.Constants;
import Utils.Scan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ReviewsAndRatingsView {
    Scanner sc = Constants.sc;
    public void displayReviewByParkingId(int parkingID) throws SQLException
    {
        UserUtils userUtils = new UserUtils();
        String query = "Select * from reviews_and_ratings WHERE parking_id = "+parkingID;
        Statement stmt = Constants.stmt;
        ResultSet rs = stmt.executeQuery(query);
        try {
            if(!rs.next()){
                Constants.printAndSpeak("No reviews for this parking\n");
            }
            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String reviews = rs.getString("reviews");
                int ratings = rs.getInt("ratings");
                String email = userUtils.getUserById(user_id).email;

                System.out.println("-------------------------------------------------------------------------");
                System.out.println("User Email: "+email);
                System.out.println("Reviews: "+reviews);
                System.out.println("Ratings: "+ratings);
                System.out.println("-------------------------------------------------------------------------");
            }

        } catch (Exception e) {
            System.out.println("Enter Exception");
            e.printStackTrace();
        }
        rs.close();
    }

    public void displayAddReview(){

        int userId = Constants.loggedInUser.user_id;

        Constants.printAndSpeak("Enter the ID of the Parking Slot you want to review: ");
        int parkingID = Integer.parseInt(Scan.nextLine());

        Constants.printAndSpeak("Enter the review: ");
        String review = Scan.nextLine();

        Constants.printAndSpeak("Enter the ratings (0 to 5): ");
        double rating = acceptRating();

        Review reviewObj = new Review(-1,userId,parkingID,review,rating);

        AddReviewsAndRatings addReviewsAndRatings = new AddReviewsAndRatings();
        addReviewsAndRatings.addReviewAndRating(reviewObj);

    }

    private double acceptRating(){
        double rating = Double.parseDouble(Scan.nextLine());
        if(rating > 5 || rating < 0){
            Constants.printAndSpeak("Rating should be between 0 to 5. Please try again: ");
            return acceptRating();
        }
        return rating;
    }
}
