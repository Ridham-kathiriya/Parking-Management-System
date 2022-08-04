package Modules.Booking;

import Modules.Booking.controller.BookingController;
import Modules.Booking.model.Booking;
import Modules.User.model.User;
import Utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookingUtilitiesTest {

    @InjectMocks
    BookingController bu;

    @Test
    void bookingPositiveTest() {
        Assertions.assertDoesNotThrow(()->{
            Booking booking = mock(Booking.class);
            Constants.conn = Mockito.mock(Connection.class);
            //BookingController bu = mock(BookingController.class);
            PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
            Mockito.when(Constants.conn.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
            Constants.setUser(Mockito.mock(User.class));
            Constants.loggedInUser.email = "motobha@gamil.com";
            Mockito.when(Mockito.mock(BookingController.class).sendEmailNotification(Constants.loggedInUser.email, "test", "Test Subject")).thenReturn(true);
            bu.book_slot(booking);
        });
    }

    @Test
    public void testEditBookingPositiveCase() {
        Assertions.assertDoesNotThrow(()->{
            Constants.conn = Mockito.mock(Connection.class);
            PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
            Mockito.when(Constants.conn.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
            boolean returnValue = bu.edit_booking_date("1234", LocalDate.of(2023,2,23));
            Assertions.assertTrue(returnValue);
        });
    }

    @Test
    public void testEditBookingTime(){
        Assertions.assertDoesNotThrow(()->{
            Constants.conn = Mockito.mock(Connection.class);
            PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
            Mockito.when(Constants.conn.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
            boolean returnValue = bu.edit_booking_time("1234", LocalTime.MIDNIGHT, LocalTime.NOON);
            Assertions.assertTrue(returnValue);
        });
    }

    @Test
    public void testDeleteBooking() throws SQLException {
        Assertions.assertDoesNotThrow(()->{
            Constants.conn = Mockito.mock(Connection.class);
            Statement st = Mockito.mock(Statement.class);
            Mockito.when(Constants.conn.createStatement()).thenReturn(st);
            Mockito.when(st.executeQuery(Mockito.anyString())).thenReturn(Mockito.mock(ResultSet.class));
            bu.delete_booking("123");
        });
    }

    @Test
    public void testGetBookings(){
        Assertions.assertDoesNotThrow(()->{
            Constants.conn = Mockito.mock(Connection.class);
            Statement st = Mockito.mock(Statement.class);
            Mockito.when(Constants.conn.createStatement()).thenReturn(st);
            Mockito.when(st.executeQuery(Mockito.anyString())).thenReturn(Mockito.mock(ResultSet.class));
            bu.get_booking("user_id","1");
        });
    }

    @Test
    public void testBookingModel(){
        Booking b = new Booking();
        b.setOwner_id(1);
        b.setUser_id(2);
        b.setBooking_date(new Date(20220410));
        b.setEnd_time(new Time(1120));
        b.setStart_time(new Time(1340));
        b.setParking_id(12);


        Assertions.assertEquals(b.getOwner_id(),1);
        Assertions.assertEquals(b.getUser_id(),2);
        Assertions.assertEquals(b.getParking_id(),12);
        Assertions.assertDoesNotThrow(()->{
            b.getBooking_date();
            b.getStart_time();
            b.getEnd_time();
        });
    }
}
