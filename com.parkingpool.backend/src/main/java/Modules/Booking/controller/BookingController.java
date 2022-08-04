package Modules.Booking.controller;

import Modules.Booking.BookingView;
import Modules.Booking.model.Booking;
import Modules.User.model.User;
import Utils.Constants;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Properties;


public class BookingController {

    String tablename = "booking";
    User loggedInUser = Constants.loggedInUser;


    public ArrayList<Booking> viewMyBookings(){
        BookingView bookingView = new BookingView();
        try{
            bookingView.displayBookings(get_booking("client_user_id",String.valueOf(loggedInUser.user_id)));
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /*
    * Description: Fetch all the bookings for a user from the database
    *
    * Parameters: search_key, search_value
    *
    * Return: List of booking objects
    *
     */

    public ArrayList<Booking> get_booking(String key, String Value) throws SQLException {

        String query = "Select * from " + tablename + " Where `"+key+"`="+Value;

        Statement st = Constants.conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        ArrayList<Booking>  b_list = new ArrayList<Booking>();
        int i = 0;

        while(rs.next()){
            Booking b = new Booking();
            b.setReference_id(rs.getString("reference_id"));
            b.setUser_id(rs.getInt("client_user_id"));
            b.setOwner_id(rs.getInt("owner_user_id"));
            b.setParking_id(rs.getInt("parking_id"));
            b.setBooking_date(rs.getDate("date"));
            b.setStart_time(rs.getTime("start_time"));
            b.setEnd_time(rs.getTime("end_time"));

            b_list.add(b);
        }
        st.close();
        return b_list;
    }

    /*
     * Description: books a parking slot for the user by updating the database and sends email notification to user
     *
     * Parameters: Booking object with all the details
     *
     * Return: New Booking reference id
     *
     */

    public String book_slot(Booking new_slot) throws SQLException {

        if(new_slot.getReference_id() == null){
            //creating a random ref Id which will be unique
            String ref = ""+new_slot.getUser_id()+"_"+(int)(Math.random()*1000);
            new_slot.setReference_id(ref);
        }

        //Building unique insert query
        String query = "Insert into " + tablename +
                " (reference_id, client_user_id, owner_user_id, parking_id, date,start_time, end_time )" +
                "Values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pst = Constants.conn.prepareStatement(query);

            pst.setString(1,new_slot.getReference_id());
            pst.setInt(2,new_slot.getUser_id());
            pst.setInt(3,new_slot.getOwner_id());
            pst.setInt(4,new_slot.getParking_id());
            pst.setDate(5,new_slot.getBooking_date());
            pst.setTime(6,new_slot.getStart_time());
            pst.setTime(7,new_slot.getEnd_time());

            pst.execute();

            //sending success notification on user email
            String mailBody = "Your Parking has been confirmed for date: " + new_slot.getBooking_date() + " from " + new_slot.getStart_time() + " to " + new_slot.getEnd_time();
            String mailSubject = "Parking Booking Confirmation";
            sendEmailNotification(Constants.loggedInUser.email, mailBody, mailSubject);
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return new_slot.getReference_id();
    }

    /*
     * Description: Update the booking time for a booking
     *
     * Parameters: booking reference_id, new start_time, new end_time
     *
     * Return: boolean status
     *
     */

    public boolean edit_booking_time(String booking_reference_id, LocalTime Start_time, LocalTime end_time) throws SQLException {
        String query1 = "Update "+tablename+" set start_time=? where reference_id='"+booking_reference_id+"'";
        String query2 = "Update "+tablename+" set end_time=? where reference_id='"+booking_reference_id+"'";

        try {

            PreparedStatement st1 = Constants.conn.prepareStatement(query1);
            st1.setTime(1, Time.valueOf(Start_time));

            PreparedStatement st2 = Constants.conn.prepareStatement(query2);
            st2.setTime(1, Time.valueOf(end_time));

            st1.execute();
            st2.execute();
            return true;
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return false;
    }

    /*
     * Description: Updates the booking date for a booking
     *
     * Parameters: booking reference id and new Date
     *
     * Return: Boolean status
     *
     */
    public boolean edit_booking_date(String booking_reference_id, LocalDate date) throws SQLException {
        String query = "Update "+tablename+" set date=? where reference_id='"+booking_reference_id+"'";

        try {
            PreparedStatement pst = Constants.conn.prepareStatement(query);
            pst.setDate(1, Date.valueOf(date));
            pst.execute();
            return true;
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return false;
    }

    /*
     * Description: Deletes a booking for the user
     *
     * Parameters: booking reference id
     *
     * Return: boolean status
     *
     */
    public boolean delete_booking(String booking_reference_id) throws SQLException {
        String query = "Delete from " + tablename + " Where reference_id='" + booking_reference_id + "'";

        try{
            Statement st = Constants.conn.createStatement();
            st.execute(query);
            Constants.printAndSpeak("Booking cancelled Successfully");
            return true;
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return false;
    }

    /*
     * Description: Utility to send email
     *
     * Parameters: email_id, message
     *
     * Return: boolean status
     *
     */
    public boolean sendEmailNotification(String email_id, String text, String subject) {
        final String username = "parkingpoolasdc@gmail.com";
        final String password = "abc@12345678";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("parkingpoolasdc@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email_id)
            );
            message.setSubject(subject);
            message.setText("Dear user, \n\n"
                    + text);

            Transport.send(message);

            System.out.println("Email notification sent to user");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }

}
