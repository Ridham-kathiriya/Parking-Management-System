package Modules.Ticket.controller;

import Modules.Booking.controller.BookingController;
import Modules.Ticket.model.TICKET_STATUS;
import Utils.Constants;

import java.sql.SQLException;
import java.sql.Statement;

public class UpdateTicketStatus {
    Statement stmt = Constants.stmt;
    public void updateTicketStatus(int ticket_id) {
        TICKET_STATUS status = TICKET_STATUS.CLOSED;
        String updateQuery = " UPDATE Ticket "+"SET status = '"+status+"' WHERE id = "+ticket_id ;
        try {
            stmt.executeUpdate(updateQuery);
            String text = "The ticket with ID "+ticket_id+" has been closed";
            String subjectText = "Ticket closed for Parking Pool";
            BookingController bookingController = new BookingController();
            bookingController.sendEmailNotification(Constants.loggedInUser.email, text, subjectText );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

