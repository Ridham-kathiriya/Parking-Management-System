package Modules.ReviewsAndRatings;

import Modules.Review.ReviewsAndRatingsView;
import Modules.Review.controller.AddReviewsAndRatings;
import Modules.Review.controller.DeleteReviewAndRating;
import Modules.Review.controller.UpdateReviewAndRating;
import Modules.Review.model.Review;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;


import java.sql.Connection;
import static org.mockito.Mockito.mock;

public class ReviewsAndRatingsTest {
    @InjectMocks
    AddReviewsAndRatings addReviewsAndRatings = mock(AddReviewsAndRatings.class);
    DeleteReviewAndRating deleteReviewAndRating = mock(DeleteReviewAndRating.class);
    UpdateReviewAndRating updateReviewAndRating = mock(UpdateReviewAndRating.class);
    ReviewsAndRatingsView reviewAndRatingsView = mock(ReviewsAndRatingsView.class);

    @Test
    void testAddReviewsAndRatings()
    {
        Connection connection = mock(Connection.class);
        boolean returnValue = addReviewsAndRatings.addReviewAndRating(new Review(10, 20, 5,"best app", 3));
    }
    @Test
    void testDeleteReviewAndRating()
    {
        Assertions.assertDoesNotThrow(()->{
            Connection connection = mock(Connection.class);
            deleteReviewAndRating.deleteReviewAndRating(123);
        });
    }
    @Test
    void testUpdateReviewAndRating()
    {
        Assertions.assertDoesNotThrow(()->{
            Connection connection = mock(Connection.class);
            updateReviewAndRating.updateReviewAndRating(new Review(10, 20, 5,"best app", 3));
        });
    }
    @Test
    void testReviewsAndRatingsView()
    {
        Assertions.assertDoesNotThrow(()->{
            Connection connection = mock(Connection.class);
            reviewAndRatingsView.displayReviewByParkingId(45);
        });
    }
}
