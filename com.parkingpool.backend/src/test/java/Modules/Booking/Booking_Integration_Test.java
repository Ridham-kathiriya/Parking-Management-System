package Modules.Booking;

import Modules.Booking.controller.BookingController;
import Modules.Booking.model.Booking;
import Modules.User.model.USER_TYPE;
import Modules.User.model.User;
import Utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

class Booking_Integration_Test {

    final String DB_URL = "jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_2_DEVINT";
    final String USER="CSCI5308_2_DEVINT_USER";
    final String PASS="phoo3saezeeGoop2";


    Booking_Integration_Test() throws SQLException {
        Constants.conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Constants.loggedInUser = new User("Test User", "test-user@yopmail.com", USER_TYPE.CUSTOMER, "Test Address", "test-unhashed-password", 999);
    }

    BookingController bu = new BookingController();

    @Test
    public void bookingCreationAndRetreivingTest() throws SQLException {
        Booking b =  new Booking();
        b.setBooking_date(Date.valueOf(LocalDate.of(2022,2,24)));
        b.setStart_time(Time.valueOf(LocalTime.of(10,30)));
        b.setEnd_time(Time.valueOf(LocalTime.of(11,30)));

        b.setOwner_id(210);
        b.setUser_id(111);
        b.setParking_id(56);

        String reference_id  = bu.book_slot( b);

        ArrayList<Booking> b_list = bu.get_booking("client_user_id", "111");
        //Booking b_ref;
        AtomicBoolean status = new AtomicBoolean(false);
        b_list.forEach((b_ref) -> {
            if(reference_id.equals(b_ref.getReference_id())){
                status.set(true);
            }
        });

        Assertions.assertTrue(status.get());
    }

    @Test
    public void editBookingDateTest() throws SQLException {
        Assertions.assertTrue(bu.edit_booking_date( "111_484", LocalDate.of(2022,2,27)));
    }

    @Test
    public void editBookingTimeTest() throws SQLException {
        Assertions.assertTrue(bu.edit_booking_time("111_484", LocalTime.of(14,20), LocalTime.of(15,20)));
    }

    @Test
    public void emailNotificationTest(){
        Assertions.assertTrue(bu.sendEmailNotification("motobha@gmail.com", "Test message", "Test subject"));
    }
}
