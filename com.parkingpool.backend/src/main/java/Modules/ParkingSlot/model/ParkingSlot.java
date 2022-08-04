package Modules.ParkingSlot.model;

import java.sql.Time;

public class ParkingSlot {
    public int parking_slot_id;
    public int distance_from_elevator;
    public String address;
    public int is_handicap;
    public double longitude;
    public double latitude;
    public double hourly_rate;
    public int is_on_street;
    public int owner_user_id;
    public Time start_time;
    public Time end_time;

    public ParkingSlot(int parking_slot_id,
                       int distance_from_elevator,
                       String address,
                       int is_handicap,
                       double longitude,
                       double latitude,
                       double hourly_rate,
                       int is_on_street,
                       int owner_user_id,
                       Time start_time,
                       Time end_time) {
        this.parking_slot_id = parking_slot_id;
        this.distance_from_elevator = distance_from_elevator;
        this.address = address;
        this.is_handicap = is_handicap;
        this.longitude = longitude;
        this.latitude = latitude;
        this.hourly_rate = hourly_rate;
        this.is_on_street = is_on_street;
        this.owner_user_id = owner_user_id;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public double getHourlyRate(){
        return hourly_rate;
    }
}
