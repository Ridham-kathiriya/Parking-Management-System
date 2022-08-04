package Modules.Ticket.controller;

import Modules.Booking.controller.BookingController;
import Modules.Ticket.model.TICKET_STATUS;
import Utils.Constants;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateTicket {
    Statement stmt = Constants.stmt;
    public void createTicket(String issue)
    {
        String insertQuery = "INSERT INTO Ticket (user_id, issue,status) VALUES(" + Constants.loggedInUser.user_id + " , " + issue + "', '" + TICKET_STATUS.OPEN + "');";
        try {
            stmt.executeUpdate(insertQuery);
            String text = "Thank you, " + Constants.loggedInUser.name + ". Your ticket has been raised succesfully!";
            String subjectText = "Ticket Raised for Parking Pool";
            BookingController bookingController = new BookingController();
            bookingController.sendEmailNotification(Constants.loggedInUser.email, text, subjectText );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
