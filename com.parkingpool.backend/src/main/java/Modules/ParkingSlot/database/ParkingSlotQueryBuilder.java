package Modules.ParkingSlot.database;

import java.sql.Time;

public class ParkingSlotQueryBuilder implements ParkingSlotQueryBuilderDAO{

    private static ParkingSlotQueryBuilder parkingSlotQueryBuilder;
    public ParkingSlotQueryBuilder(){
        //Required empty constructor
    }

    public static ParkingSlotQueryBuilder getInstance(){
        if(parkingSlotQueryBuilder == null){
            parkingSlotQueryBuilder = new ParkingSlotQueryBuilder();
        }
        return parkingSlotQueryBuilder;
    }

    @Override
    public String AddParkingSlotQueryBuilder(int distance_from_elevator,
    String address,
    int is_handicap,
    double longitude,
    double latitude,
    double hourly_rate,
    int is_on_street,
    int owner_user_id,
    Time start_time,
    Time end_time) {
        return "INSERT INTO ParkingSlot (distance_from_elevator, address, is_handicap, longitude, latitude, hourly_rate, is_on_street, owner_user_id, start_time, end_time) VALUES('"+
                distance_from_elevator + "','" +
                address + "','" +
                is_handicap + "','" +
                longitude + "','"+
                latitude+"','"+
                hourly_rate+"','"+
                is_on_street+"','"+
                owner_user_id+"',"+
                "'"+start_time+"',"+
                "'"+end_time+"')";
    }

    @Override
    public String FindAllParkingSlotsQueryBuilder() {
        return "SELECT * from ParkingSlot";
    }

    @Override
    public String DeleteParkingSlotQueryBuilder(int parkingSlotId, int userId) {
        return "DELETE FROM ParkingSlot WHERE id="+parkingSlotId+" AND owner_user_id="+userId;
    }

    @Override
    public String FindParkingSlotByIdQueryBuilder(int parkingSlotId) {
        return "SELECT * FROM ParkingSlot WHERE id="+parkingSlotId;
    }
}