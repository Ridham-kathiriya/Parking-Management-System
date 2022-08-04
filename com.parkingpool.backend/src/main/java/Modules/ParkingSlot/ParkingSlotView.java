package Modules.ParkingSlot;

import Modules.Analytics.AnalyticsView;
import Modules.Booking.BookingView;
import Modules.Booking.controller.BookingController;
import Modules.Booking.model.Booking;
import Modules.ParkingSlot.Utils.ParkingSlotUtils;
import Modules.ParkingSlot.controller.AddParkingSlot;
import Modules.ParkingSlot.controller.DeleteParkingSlot;
import Modules.ParkingSlot.controller.FindParkingSlots;
import Modules.ParkingSlot.database.ParkingSlotQueryBuilder;
import Modules.ParkingSlot.database.ParkingSlotQueryBuilderDAO;
import Modules.ParkingSlot.model.ParkingSlot;
import Modules.Review.ReviewsAndRatingsView;
import Modules.Ticket.TicketView;
import Modules.User.model.USER_TYPE;
import Modules.User.model.User;
import Utils.Constants;
import Utils.GoogleMap;
import Utils.Scan;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ParkingSlotView {
    User loggedInUser;
    Statement stmt = Constants.stmt;
    ParkingSlotQueryBuilderDAO parkingSlotQueryBuilderDAO = ParkingSlotQueryBuilder.getInstance();

    // ----- PUBLIC ITEMS -----
    public ParkingSlotView(User user) {
        loggedInUser = user;
        Constants.setParkingSlotQueryBuilderDao(parkingSlotQueryBuilderDAO);
    }

    public boolean displayParkingSlotMenu() throws SQLException, ParseException {
        boolean toContinue = true;
        switch (loggedInUser.role) {
            case VENDOR:
                toContinue = displayVendorMenu();
                break;
            case CUSTOMER:
                toContinue = displayCustomerMenu();
                break;
            case ADMIN:
                toContinue = displayAdminMenu();
                break;
            default:
                System.out.println("User role not recognized");
                System.exit(0);
        }
        return toContinue;
    }


    // ----- PRIVATE ITEMS -----
    // ----- For displaying Vendor specific menu -----
    private boolean displayVendorMenu() {
        Constants.printAndSpeak("Enter the following numbers to access the corresponding item:\n1: Add Parking Slot.\n2: View My Parking Slots.\n3: Exit ParkingPool.\nEnter your command: ");
        boolean toContinue = true;
        int input = Integer.parseInt(Scan.nextLine());
        switch (input) {
            case 1:
                AddParkingSlot addParkingSlot = new AddParkingSlot(parkingSlotQueryBuilderDAO);
                addParkingSlot.AddParkingSlots(addParkingSlotDetails());
                toContinue = true;
                break;
            case 2:
                try {
                    displayMyParkingSlots();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                toContinue = true;
                break;
            case 3:
                Constants.printAndSpeak("See you soon!");
                toContinue = false;
                break;
            default:
                Constants.printAndSpeak("Incorrect input.");
                displayVendorMenu();
                break;
        }
        return toContinue;
    }

    //----- For displaying Customer specific menu -----
    public boolean displayCustomerMenu() throws SQLException, ParseException {
        Constants.printAndSpeak("Enter the following numbers to access the corresponding item:\n1: View Parking Slots.\n2. View My Bookings\n3. Raise a ticket. \n4: Exit ParkingPool.\nEnter your command: ");
        boolean toContinue = true;
        int input = Integer.parseInt(Scan.nextLine());
        switch (input) {
            case 1:
                FindParkingSlots findParkingSlots = new FindParkingSlots();
                Constants.printAndSpeak("Enter the Google Maps URL of the location you want to find nearby parking locations to: ");
                Map<String, String> map = GoogleMap.parseUrl(Scan.nextLine().trim());

                double longitude = Double.parseDouble(map.get("longitude"));
                double latitude = Double.parseDouble(map.get("latitude"));

                Constants.printAndSpeak("Enter the date (yyyy-mm-dd) you want to find the Parking Slot for: ");
                Date date = Date.valueOf(Scan.nextLine());
                Constants.printAndSpeak("Enter the time (hh:mm:ss) you want to book your slot for: ");
                LocalTime startTime = LocalTime.parse(Scan.nextLine());
                Constants.printAndSpeak("Enter the time (hh:mm:ss) you want to end your booking: ");
                LocalTime endTime = LocalTime.parse(Scan.nextLine());
                Constants.printAndSpeak("Do you require the Parking Slot to be handicapped accessible? Yes or No: ");
                boolean handicappedAccessible = Scan.nextLine().toLowerCase().charAt(0) == 'y';
                ArrayList<ParkingSlot> foundParkingSlots = findParkingSlots.findAvailableParkingSlots(longitude, latitude, date, startTime, endTime, handicappedAccessible);

                ParkingSlotUtils.viewParkingSlots(foundParkingSlots);
                Constants.printAndSpeak("Enter the following numbers to access the corresponding item: \n1. Book a Parking Slot from above\n2. Write a review.\n3. Sort according to rate\n4. Sort according to distance from elevator.\n5. Go Back\nEnter your command: ");
                int bookInput = Integer.parseInt(Scan.nextLine());

                switch (bookInput) {
                    case 1:
                        Constants.printAndSpeak("Enter the selected Parking ID: ");
                        int parkingId = Integer.parseInt(Scan.nextLine());
                        BookAParkingSlot(parkingId, date, startTime, endTime);
                        break;
                    case 2:
                        ReviewsAndRatingsView reviewsAndRatingsView = new ReviewsAndRatingsView();
                        reviewsAndRatingsView.displayAddReview();
                        break;
                    case 3:
                        ArrayList<ParkingSlot> sortedRateParkingSlots = findParkingSlots.sortAccordingToRate(foundParkingSlots);
                        ParkingSlotUtils.viewParkingSlots(sortedRateParkingSlots);
                        break;
                    case 4:
                        ArrayList<ParkingSlot> sortedDistanceFromElevatorParkingSlots = findParkingSlots.sortAccordingToDistanceFromElevator(foundParkingSlots);
                        ParkingSlotUtils.viewParkingSlots(sortedDistanceFromElevatorParkingSlots);
                    default:
                        Constants.printAndSpeak("Incorrect input.");
                        break;
                }
                break;
            case 2:
                BookingController bookingController = new BookingController();
                bookingController.viewMyBookings();
                BookingView bookingView = new BookingView();
                bookingView.displayModifyBookingMenu();
                break;
            case 3:
                TicketView ticketView = new TicketView();
                ticketView.createTicket();
                break;
            case 4:
                Constants.printAndSpeak("See you soon!");
                System.exit(0);
                break;
            default:
                Constants.printAndSpeak("Incorrect input.");
                displayCustomerMenu();
                break;
        }
        return toContinue;
    }

    // ----- For displaying Admin specific Menu
    private boolean displayAdminMenu() throws SQLException {
        AnalyticsView analyticsView = new AnalyticsView();
        boolean toContinue = true;
        Constants.printAndSpeak("Enter the following number to access the corresponding item:\n1. Show Analysis of the data for ParkingPool\n2. Generate a Spreadsheet for the analytics\n3. Exit ParkingPool");
        int input = Integer.parseInt(Scan.nextLine());
        switch (input) {
            case 1:
                analyticsView.showAnalytics();
                toContinue = true;
                break;
            case 2:
                analyticsView.createAnalyticsCSV();
                toContinue = true;
                break;
            case 3:
                TicketView ticketView = new TicketView();
                ticketView.displayAdminTicketMenu();
                break;
            case 4:
                Constants.printAndSpeak("See you soon!");
                toContinue = false;
                break;
            default:
                Constants.printAndSpeak("Unknown item accessed! Try again!");
                toContinue = true;
                break;
        }
        return toContinue;
    }

    //Displays All Parking Slots of the loggedIn User.
    private void displayMyParkingSlots() throws SQLException {
        if (loggedInUser.role != USER_TYPE.VENDOR) {
            Constants.printAndSpeak("User is not a Vendor!");
        } else {
            String myParkingSlotsQuery = "SELECT * from ParkingSlot where owner_user_id=" + loggedInUser.user_id;
            ResultSet myParkingSlotsResultSet = stmt.executeQuery(myParkingSlotsQuery);
            ArrayList<ParkingSlot> myParkingSlots = ParkingSlotUtils.ResultSetToParkingSlot(myParkingSlotsResultSet);
            ParkingSlotUtils.viewParkingSlots(myParkingSlots);
            boolean toContinue = true;
            while (toContinue) {
                int selectedItem = displayEditParkingSlotMenu();
                switch (selectedItem) {
                    case 1:
                        DeleteParkingSlot deleteParkingSlot = new DeleteParkingSlot(parkingSlotQueryBuilderDAO);
                        Constants.printAndSpeak("Enter the Parking Slot ID you want to delete: ");
                        deleteParkingSlot.deleteParkingSlot(Integer.parseInt(Scan.nextLine()), loggedInUser.user_id);
                        toContinue = true;
                        break;
                    case 2:
                        ReviewsAndRatingsView reviewsAndRatingsView = new ReviewsAndRatingsView();
                        Constants.printAndSpeak("Enter the Parking Slot ID you want to view reviews for: ");
                        reviewsAndRatingsView.displayReviewByParkingId(Integer.parseInt(Scan.nextLine()));
                        toContinue = true;
                        break;
                    default:
                        toContinue = false;
                        Constants.printAndSpeak("Going back to previous menu!\n");
                        break;
                }
            }
        }
    }

    //Displays Edit Parking Slot Menu of the logged in user
    private int displayEditParkingSlotMenu() {
        Constants.printAndSpeak("*** My Parking Slots ***\n1. Delete a Parking Slot.\n2. View Reviews.\n3. Go back.\nEnter your command: ");
        return Integer.parseInt(Scan.nextLine().trim());
    }

    // This method will fetch all the parking slot details from the user and return a ParkingSlot.
    private ParkingSlot addParkingSlotDetails() {
        Constants.printAndSpeak("Enter Google Map URL of the location: ");
        String googleMap = Scan.nextLine().trim();

        Map<String, String> parsedGoogleMap = GoogleMap.parseUrl(googleMap);

        String address = parsedGoogleMap.get("address");
        double longitude = Double.parseDouble(parsedGoogleMap.get("longitude"));
        double latitude = Double.parseDouble(parsedGoogleMap.get("latitude"));

        Constants.printAndSpeak("\nEnter the distance from elevator(enter 0 if there is no elevator): ");
        int distance_from_elevator = Integer.parseInt(Scan.nextLine().trim());

        Constants.printAndSpeak("\nIs it for handicap? Yes or No: ");
        int is_handicap = Integer.parseInt(String.valueOf(Scan.nextLine().toUpperCase().startsWith("Y") ? '1' : '0'));

        Constants.printAndSpeak("\nIs the parking on street? Yes or No: ");
        int is_on_street = Integer.parseInt(String.valueOf(Scan.nextLine().toUpperCase().startsWith("Y") ? '1' : '0'));

        Constants.printAndSpeak("\nEnter the hourly rate of the parking slot: ");
        float hourly_rate = Float.parseFloat(Scan.nextLine().trim());

        Constants.printAndSpeak("\nEnter the starting hour (hh:mm:ss) of the Parking Slot: ");
        Time start_time = Time.valueOf(Scan.nextLine().trim());

        Constants.printAndSpeak("\nEnter the ending hour (hh:mm:ss) of the Parking Slot: ");
        Time end_time = Time.valueOf(Scan.nextLine().trim());

        int owner_user_id = loggedInUser.user_id;

        ParkingSlot parkingSlot = new ParkingSlot(-1, distance_from_elevator, address, is_handicap, longitude, latitude, hourly_rate, is_on_street, owner_user_id, start_time, end_time);
        return parkingSlot;
    }

    private void BookAParkingSlot(int parking_Id, Date date, LocalTime start_time, LocalTime end_time) {
        Booking booking = new Booking();
        booking.setBooking_date((java.sql.Date) date);
        booking.setParking_id(parking_Id);
        booking.setStart_time(Time.valueOf(start_time));
        booking.setEnd_time(Time.valueOf(end_time));
        booking.setUser_id(loggedInUser.user_id);
        booking.setOwner_id(2);

        BookingController bookingUtilities = new BookingController();
        try {
            bookingUtilities.book_slot(booking);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
