package Modules.ParkingSlot.controller;

import Modules.ParkingSlot.database.ParkingSlotQueryBuilderDAO;
import Modules.ParkingSlot.model.ParkingSlot;
import Utils.Constants;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class AddParkingSlot {
    Statement stmt = Constants.stmt;
    private final ParkingSlotQueryBuilderDAO parkingSlotQueryBuilderDAO;

    public AddParkingSlot(ParkingSlotQueryBuilderDAO parkingSlotQueryBuilderDAO){
        this.parkingSlotQueryBuilderDAO = parkingSlotQueryBuilderDAO;
    }

    public boolean AddParkingSlots(ParkingSlot parkingSlot) {
        int distance_from_elevator = parkingSlot.distance_from_elevator;
        String address = parkingSlot.address;
        int is_handicap = parkingSlot.is_handicap;
        double longitude = parkingSlot.longitude;
        double latitude = parkingSlot.latitude;
        double hourly_rate = parkingSlot.hourly_rate;
        int is_on_street = parkingSlot.is_on_street;
        int owner_user_id = parkingSlot.owner_user_id;
        Time start_time = parkingSlot.start_time;
        Time end_time = parkingSlot.end_time;

        String addParkingSlotQuery = parkingSlotQueryBuilderDAO.AddParkingSlotQueryBuilder(
                distance_from_elevator,
                address,
                is_handicap,
                longitude,
                latitude,
                hourly_rate,
                is_on_street,
                owner_user_id,
                start_time,
                end_time
        );
        boolean isAdded = false;
        try {
            isAdded = Constants.stmt.execute(addParkingSlotQuery);
            if(isAdded){
                System.out.println("**** Parking Slot has been added *****");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }
}
