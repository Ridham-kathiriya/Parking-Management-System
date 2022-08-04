package Modules.Review.model;

public class Review {

    private int review_id;
    private int userID;
    private int parkingID;
    private String reviews;
    private double ratings;
    public Review(int review_id, int userID, int parkingID, String reviews, double ratings)

    {
        this.review_id = review_id;
        this.userID = userID;
        this.parkingID = parkingID;
        this.reviews = reviews;
        this.ratings = ratings;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getParkingID() {
        return parkingID;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }
}