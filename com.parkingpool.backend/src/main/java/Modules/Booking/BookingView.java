package Modules.Booking;

import Modules.Booking.controller.BookingController;
import Modules.Booking.model.Booking;
import Modules.ParkingSlot.Utils.ParkingSlotUtils;
import Modules.ParkingSlot.model.ParkingSlot;
import Utils.Constants;
import Utils.GoogleMap;
import Utils.Scan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class BookingView {
    ParkingSlotUtils parkingSlotUtils = new ParkingSlotUtils(Constants.parkingSlotQueryBuilderDAO);
    BookingController bookingController = new BookingController();
    public void displayModifyBookingMenu() throws SQLException {
        Constants.printAndSpeak("Enter the following numbers to access the corresponding item: \n1. Delete a Booking.\n2. Go Back\nEnter your command: ");
        switch (Integer.parseInt(Scan.nextLine())) {
            case 1:
                Constants.printAndSpeak("Enter the booking ID you want to delete: ");
                String modifyBookingId = Scan.nextLine();
                bookingController.delete_booking(modifyBookingId);
                break;
            default:
                break;

        }
    }

    public void displayBookings(ArrayList<Booking> bookings) {
        ArrayList<Booking> pastBookings = new ArrayList<>();
        ArrayList<Booking> upcomingBookings = new ArrayList<>();
        Date currentDate = new Date();
        bookings.forEach((b) -> {
            if (b.getBooking_date().after(currentDate) && b.getStart_time().after(currentDate)) {
                upcomingBookings.add(b);
            } else {
                pastBookings.add(b);
            }
        });

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("                        *** Upcoming Bookings ***                        ");
        System.out.println("-------------------------------------------------------------------------");
        printBookings(upcomingBookings);
        System.out.println("\n\n");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("                           *** Past Bookings ***                         ");
        System.out.println("-------------------------------------------------------------------------");
        printBookings(pastBookings);
    }

    private void printBookings(ArrayList<Booking> bookings) {
        if (bookings.size() == 0) {
            System.out.println("-------------------------------------------------------------------------");
            Constants.printAndSpeak("There are no bookings");
            System.out.println("-------------------------------------------------------------------------");
        } else {
            bookings.forEach((b) -> {
                ParkingSlot parkingSlot = null;
                try {
                    parkingSlot = parkingSlotUtils.getParkingSlotById(b.getParking_id());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Booking ID: " + b.getReference_id());
                System.out.println("Booking Date: " + b.getBooking_date());
                System.out.println("Start Time: " + b.getStart_time());
                System.out.println("End Time: " + b.getEnd_time());
                System.out.println("Parking Slot Location: " + GoogleMap.generateUrl(parkingSlot.address));
                System.out.println("-------------------------------------------------------------------------");
            });
        }
    }
}
