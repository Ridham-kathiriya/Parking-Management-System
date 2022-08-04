package Modules.ParkingSlot.Utils;

import Modules.ParkingSlot.database.ParkingSlotQueryBuilderDAO;
import Modules.ParkingSlot.model.ParkingSlot;
import Utils.Constants;
import Utils.GoogleMap;

import java.sql.*;
import java.util.ArrayList;

public class ParkingSlotUtils {
    private final ParkingSlotQueryBuilderDAO parkingSlotQueryBuilderDAO;
    private Statement stmt = Constants.stmt;

    public ParkingSlotUtils(ParkingSlotQueryBuilderDAO parkingSlotQueryBuilderDAO) {
        this.parkingSlotQueryBuilderDAO = parkingSlotQueryBuilderDAO;
    }

    public ArrayList<ParkingSlot> FindAllParkingSlots() throws SQLException {
        String findAllParkingSlotQuery = parkingSlotQueryBuilderDAO.FindAllParkingSlotsQueryBuilder();
        ResultSet parkingSlotResultSet = Constants.stmt.executeQuery(findAllParkingSlotQuery);
        ArrayList<ParkingSlot> parkingSlots = ResultSetToParkingSlot(parkingSlotResultSet);
        return parkingSlots;
    }

    public static double calculateDistanceInMeters(double lat1, double long1, double lat2,
                                            double long2) {


        double dist = org.apache.lucene.util.SloppyMath.haversinMeters(lat1, long1, lat2, long2);
        return dist;
    }
    public static ArrayList<ParkingSlot> ResultSetToParkingSlot(ResultSet parkingSlotResultSet) throws SQLException {
        ArrayList<ParkingSlot> parkingSlots = new ArrayList<ParkingSlot>();
        while(parkingSlotResultSet.next()){
            parkingSlots.add(new ParkingSlot(
                    parkingSlotResultSet.getInt("id"),
                    parkingSlotResultSet.getInt("distance_from_elevator"),
                    parkingSlotResultSet.getString("address"),
                    parkingSlotResultSet.getInt("is_handicap"),
                    parkingSlotResultSet.getDouble("longitude"),
                    parkingSlotResultSet.getDouble("latitude"),
                    parkingSlotResultSet.getDouble("hourly_rate"),
                    parkingSlotResultSet.getInt("is_on_street"),
                    parkingSlotResultSet.getInt("owner_user_id"),
                    parkingSlotResultSet.getTime("start_time"),
                    parkingSlotResultSet.getTime("end_time")
            ));
        }
        return parkingSlots;
    }

    public static void viewParkingSlots(ArrayList<ParkingSlot> parkingSlots){
        for (int i = 0; i < parkingSlots.size(); i++) {
            ParkingSlot parkingSlot = parkingSlots.get(i);
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Parking Slot ID: " + parkingSlot.parking_slot_id);
            System.out.println("Distance from Elevator (0 if no elevator): " + parkingSlot.distance_from_elevator);
            System.out.println("Address: " + parkingSlot.address);
            System.out.println("If the Parking is on Street: " + (parkingSlot.is_on_street == 1 ? "Yes" : "No"));
            System.out.println("If the Parking is for handicap: " + (parkingSlot.is_handicap == 1 ? "Yes" : "No"));
            System.out.println("Hourly Rate: " + parkingSlot.hourly_rate);
            System.out.println("Longitude: " + parkingSlot.longitude);
            System.out.println("Latitude: " + parkingSlot.latitude);
            System.out.println("Start Time: " + parkingSlot.start_time);
            System.out.println("End Time: " + parkingSlot.end_time);
            System.out.println("Google Maps: " + GoogleMap.generateUrl(parkingSlot.address));
            System.out.println("-------------------------------------------------------------------------");
        };
    }

    public ParkingSlot getParkingSlotById(int id) throws SQLException {
        String findParkingSlotByIdQuery = parkingSlotQueryBuilderDAO.FindParkingSlotByIdQueryBuilder(id);
        ResultSet parkingSlotResultSet = stmt.executeQuery(findParkingSlotByIdQuery);
        ParkingSlot parkingSlot = ResultSetToParkingSlot(parkingSlotResultSet).get(0);
        return parkingSlot;
    }
}
