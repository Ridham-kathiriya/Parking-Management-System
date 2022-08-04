package Modules.ParkingSlot.controller;

import Modules.Booking.model.Booking;
import Modules.ParkingSlot.Utils.ParkingSlotUtils;
import Modules.ParkingSlot.model.ParkingSlot;
import Utils.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class FindParkingSlots {
    ParkingSlotUtils parkingSlotUtils = new ParkingSlotUtils(Constants.parkingSlotQueryBuilderDAO);

    public ArrayList<ParkingSlot> findNearbyParkingSlots(double longitude, double latitude) throws SQLException {

        ArrayList<ParkingSlot> nearbyParkingSlots=new ArrayList<ParkingSlot>();
        ArrayList<ParkingSlot> allParkingSlots= parkingSlotUtils.FindAllParkingSlots();
        double distanceBetweenTwoPoint=0;
        double max_distance = 10000;

        for (ParkingSlot parkingSlot:
             allParkingSlots) {
            distanceBetweenTwoPoint= parkingSlotUtils.calculateDistanceInMeters(latitude, longitude, parkingSlot.latitude, parkingSlot.longitude);
            if(distanceBetweenTwoPoint<max_distance)
            {
                nearbyParkingSlots.add(parkingSlot);
            }
        }
        return nearbyParkingSlots;
    }

    public ArrayList<ParkingSlot> findAvailableParkingSlots(double longitude, double latitude, Date date, LocalTime startTime, LocalTime endTime, boolean handicapAccessible) throws SQLException {
        String getBookedSlotsQuery = "SELECT * FROM booking WHERE date >= \"" + date + "\" and (end_time <= \""  + startTime + "\" or start_time <= \"" + endTime + "\")";
        ResultSet rs = Constants.stmt.executeQuery(getBookedSlotsQuery);
        ArrayList<Booking> bookedSlots = new ArrayList<Booking>();

        while(rs.next()){
            Booking booking = new Booking();
            booking.setParking_id(rs.getInt("parking_id"));
            booking.setBooking_date(rs.getDate("date"));
            booking.setOwner_id(rs.getInt("owner_user_id"));
            booking.setStart_time(rs.getTime("start_time"));
            booking.setUser_id(rs.getInt("client_user_id"));
            booking.setEnd_time(rs.getTime("end_time"));

            bookedSlots.add(booking);
        }

        ArrayList<ParkingSlot> nearbyParkingSlots = findNearbyParkingSlots(longitude, latitude);
        ArrayList<ParkingSlot> finalParkingSlots = new ArrayList<ParkingSlot>();

        List<Integer> booked_parking_ids = bookedSlots.stream().map(Booking::getParking_id).collect(Collectors.toList());

        for (ParkingSlot parkingSlot:
             nearbyParkingSlots) {
            if(isParkingSlotAvailable(booked_parking_ids, parkingSlot, startTime, endTime)){
                finalParkingSlots.add(parkingSlot);
            }
        }

        if(!handicapAccessible){
            int handicapCount = 0;
            for (ParkingSlot parkingSlot:
                    finalParkingSlots) {
                if(parkingSlot.is_handicap == 1){
                    handicapCount++;
                }
            }
            if(handicapCount / finalParkingSlots.size() > 0.3){
                finalParkingSlots = new ArrayList<ParkingSlot>(finalParkingSlots.stream().filter(parkingSlot -> parkingSlot.is_handicap == 1).collect(Collectors.toList()));
            }

        }
        return sortAccordingToDistance(finalParkingSlots, longitude, latitude);
    }

    public ArrayList<ParkingSlot> sortAccordingToRate(ArrayList<ParkingSlot> parkingSlots){
        Collections.sort(parkingSlots, Comparator.comparing(ParkingSlot::getHourlyRate));
        return parkingSlots;
    }

    private ArrayList<ParkingSlot> sortAccordingToDistance(ArrayList<ParkingSlot> parkingSlots, double longitude, double latitude){
        Collections.sort(parkingSlots, Comparator.comparing(parkingSlot -> parkingSlotUtils.calculateDistanceInMeters(latitude, longitude, parkingSlot.latitude, parkingSlot.longitude)));
        return parkingSlots;
    }

    public ArrayList<ParkingSlot> sortAccordingToDistanceFromElevator(ArrayList<ParkingSlot> parkingSlots){
        Collections.sort(parkingSlots, Comparator.comparing(parkingSlot -> parkingSlot.distance_from_elevator)); ;
        return parkingSlots;
    }

    // ----- PRIVATE ITEMS -----
    private boolean isParkingSlotAvailable(List<Integer> booked_parking_ids, ParkingSlot parkingSlot, LocalTime startTime, LocalTime endTime){
        return (!booked_parking_ids.contains(parkingSlot.parking_slot_id));
    }
}
